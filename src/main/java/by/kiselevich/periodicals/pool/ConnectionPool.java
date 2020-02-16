package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_CAPACITY = 15;
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_USER_PROPERTY = "db.user";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";
    private static final String DATABASE_URL_PROPERTY = "db.url";

    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> unavailableConnections;
    private volatile boolean isPoolAlreadyInitiated;

    ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>();
        unavailableConnections = new LinkedBlockingQueue<>();
        isPoolAlreadyInitiated = false;
    }


    /**
     *
     * @throws NoJDBCDriverException while no driver
     * @throws NoJDBCPropertiesException while no properties file
     */
    public synchronized void initPool() throws NoJDBCDriverException, NoJDBCPropertiesException {
        if (!isPoolAlreadyInitiated) {
            Properties databaseProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILENAME);
            try {
                databaseProperties.load(inputStream);
                String user = databaseProperties.getProperty(DATABASE_USER_PROPERTY);
                char[] password = databaseProperties.getProperty(DATABASE_PASSWORD_PROPERTY).toCharArray();
                String url = databaseProperties.getProperty(DATABASE_URL_PROPERTY);

                DriverManager.registerDriver(DriverManager.getDriver(url));

                for (int i = 0; i < POOL_CAPACITY; i++) {
                    availableConnections.add(DriverManager.getConnection(url, user, new String(password)));
                }
                isPoolAlreadyInitiated = true;
            } catch (IOException e) {
                throw new NoJDBCPropertiesException(e);
            } catch (SQLException e) {
                throw new NoJDBCDriverException(e);
            }
        }
    }

    /**
     *
     * @return Connection ready to use
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = availableConnections.take();
            unavailableConnections.put(connection);
        } catch (InterruptedException e) {
            LOG.warn(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     *
     * @param connection Connection to return
     */
    public void returnConnection(Connection connection) {
        try {
            if(!unavailableConnections.remove(connection)) {
                LOG.warn("Cant remove connection from unavailable connections");
            }
            availableConnections.put(connection);
        } catch (InterruptedException e) {
            LOG.warn(e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     */
    public synchronized void deInitPool() {
        if (isPoolAlreadyInitiated) {

            if (!unavailableConnections.isEmpty()) {
                LOG.warn("Deinitializing connection pool while not all connections returned");
            }

            closeConnections(availableConnections);
            closeConnections(unavailableConnections);

            isPoolAlreadyInitiated = false;
        }
    }

    private void closeConnections(BlockingQueue<Connection> connections) {
        while (!connections.isEmpty()) {
            try {
                connections.take().close();
            } catch (SQLException e) {
                LOG.warn("Cant close connection", e);
            } catch (InterruptedException e) {
                LOG.warn(e);
                Thread.currentThread().interrupt();
            }
        }
    }


}
