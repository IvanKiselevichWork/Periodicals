package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoConnectionAvailableException;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);

    private static final int POOL_CAPACITY = 15;
    private static final long MAXIMUM_CONNECTION_WAITING_IN_SECONDS = 60;
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "url";

    private BlockingQueue<ConnectionProxy> availableConnections;
    private Deque<ConnectionProxy> unavailableConnections;
    private AtomicBoolean isPoolAlreadyInitiated;

    ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>();
        unavailableConnections = new ArrayDeque<>();
        isPoolAlreadyInitiated = new AtomicBoolean(false);
    }

    /**
     *
     * @throws NoJDBCDriverException while no driver
     * @throws NoJDBCPropertiesException while no properties file
     */
    public void initPool() throws NoJDBCDriverException, NoJDBCPropertiesException {
        if (!isPoolAlreadyInitiated.get()) {
            Properties databaseProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILENAME);
            if (inputStream == null) {
                throw new NoJDBCPropertiesException();
            }
            try {
                databaseProperties.load(inputStream);
                String url = databaseProperties.getProperty(DATABASE_URL_PROPERTY);

                DriverManager.registerDriver(DriverManager.getDriver(url));

                for (int i = 0; i < POOL_CAPACITY; i++) {
                    availableConnections.add(new ConnectionProxy(DriverManager.getConnection(url, databaseProperties)));
                }
                isPoolAlreadyInitiated.set(true);
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
    public ConnectionProxy getConnection() throws NoConnectionAvailableException {
        ConnectionProxy connection = null;
        try {
            connection = availableConnections.poll(MAXIMUM_CONNECTION_WAITING_IN_SECONDS, TimeUnit.SECONDS);
            if (connection == null) {
                throw new NoConnectionAvailableException();
            }
            unavailableConnections.add(connection);
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
    public void returnConnection(ConnectionProxy connection) {
        try {
            if (connection != null) {
                if (!unavailableConnections.remove(connection)) {
                    LOG.warn("Cant remove connection from unavailable connections");
                }
                availableConnections.put(connection);
            }
        } catch (InterruptedException e) {
            LOG.warn(e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     */
    public void deInitPool() {
        if (isPoolAlreadyInitiated.get()) {

            if (!unavailableConnections.isEmpty()) {
                LOG.warn("Deinitializing connection pool while not all connections returned");
            }

            try {
                while (!availableConnections.isEmpty()) {
                    availableConnections.take().closeWhileDeInitPool();
                }
                while (!unavailableConnections.isEmpty()) {
                    unavailableConnections.removeLast().closeWhileDeInitPool();
                }
            } catch (SQLException e) {
                LOG.warn("Cant close connection", e);
            } catch (InterruptedException e) {
                LOG.warn(e);
                Thread.currentThread().interrupt();
            }

            isPoolAlreadyInitiated.set(false);
        }
    }


}
