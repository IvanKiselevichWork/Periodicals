package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.ConnectionPoolRuntimeException;
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
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public enum ConnectionPoolImpl implements ConnectionPool {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(ConnectionPoolImpl.class);

    private static final String POOL_NOT_INITIALIZED = "Pool not initialized";

    private static final int POOL_CAPACITY = 15;
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "url";

    private BlockingQueue<ConnectionProxy> availableConnections;
    private Deque<ConnectionProxy> unavailableConnections;
    private boolean isPoolAlreadyInitiated;
    private String url;

    ConnectionPoolImpl() {
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
                url = databaseProperties.getProperty(DATABASE_URL_PROPERTY);

                DriverManager.registerDriver(new Driver());

                for (int i = 0; i < POOL_CAPACITY; i++) {
                    availableConnections.add(new ConnectionProxy(DriverManager.getConnection(url, databaseProperties)));
                }
                isPoolAlreadyInitiated = true;
                LOG.trace("pool initialized");
            } catch (IOException e) {
                throw new NoJDBCPropertiesException(e);
            } catch (SQLException e) {
                throw new NoJDBCDriverException(e);
            }
        }
        LOG.trace("init pool ended");
    }

    /**
     * @return is pool initiated
     */
    public boolean isPoolInitiated() {
        return isPoolAlreadyInitiated;
    }

    /**
     * @return Connection ready to use
     */
    @Override
    public ConnectionProxy getConnection() {
        if (!isPoolAlreadyInitiated) {
            throw new ConnectionPoolRuntimeException(POOL_NOT_INITIALIZED);
        }
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
    @Override
    public ConnectionProxy getConnection(long waitingDuration, TimeUnit timeUnit) throws NoConnectionAvailableException {
        if (!isPoolAlreadyInitiated) {
            throw new ConnectionPoolRuntimeException(POOL_NOT_INITIALIZED);
        }
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
            deregisterDriver();

            isPoolAlreadyInitiated = false;
            LOG.trace("pool deinitialized");
        }
        LOG.trace("deInit pool ended");
    }

    private void deregisterDriver() {
        try {
            DriverManager.deregisterDriver(DriverManager.getDriver(url));
        } catch (SQLException e) {
            LOG.warn("Cant deregister driver", e);
        }

    }
}
