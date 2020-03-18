package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoConnectionAvailableException;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);

    private static final int POOL_CAPACITY = 15;
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "url";

    private BlockingQueue<ConnectionProxy> availableConnections;
    private Deque<ConnectionProxy> unavailableConnections;
    private boolean isPoolAlreadyInitiated;

    ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>();
        unavailableConnections = new ArrayDeque<>();
        isPoolAlreadyInitiated = false;
    }

    /**
     * @throws NoJDBCDriverException     while no driver
     * @throws NoJDBCPropertiesException while no properties file
     */
    public synchronized void initPool() throws NoJDBCDriverException, NoJDBCPropertiesException {
        if (!isPoolAlreadyInitiated) {
            LOG.trace("init pool started");
            Properties databaseProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILENAME);
            if (inputStream == null) {
                throw new NoJDBCPropertiesException();
            }
            try {
                databaseProperties.load(inputStream);
                String url = databaseProperties.getProperty(DATABASE_URL_PROPERTY);

                DriverManager.registerDriver(new Driver());

                for (int i = 0; i < POOL_CAPACITY; i++) {
                    availableConnections.add(new ConnectionProxy(DriverManager.getConnection(url, databaseProperties)));
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
     * @return Connection ready to use
     */
    public ConnectionProxy getConnection() {
        ConnectionProxy connection = null;
        try {
            connection = availableConnections.take();
            unavailableConnections.add(connection);
        } catch (InterruptedException e) {
            LOG.warn(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * @param waitingDuration duration for waiting in units timeUnit
     * @param timeUnit        units for waiting duration
     * @return connection ready to use
     * @throws NoConnectionAvailableException if waiting timed out and no connection
     */
    public ConnectionProxy getConnection(long waitingDuration, TimeUnit timeUnit) throws NoConnectionAvailableException {
        ConnectionProxy connection = null;
        try {
            connection = availableConnections.poll(waitingDuration, timeUnit);
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
        if (isPoolAlreadyInitiated) {
            LOG.trace("deInit pool started");
            try {
                for (int i = 0; i < POOL_CAPACITY; i++) {
                    availableConnections.take().closeWhileDeInitPool();
                }
            } catch (SQLException e) {
                LOG.warn("Cant close connection", e);
            } catch (InterruptedException e) {
                LOG.warn(e);
                Thread.currentThread().interrupt();
            }
            deregisterDrivers();

            isPoolAlreadyInitiated = false;
        }
    }

    private void deregisterDrivers() {
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOG.warn("Cant deregister driver", e);
            }
        }
    }
}
