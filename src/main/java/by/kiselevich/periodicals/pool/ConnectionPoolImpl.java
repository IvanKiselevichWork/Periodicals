package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.ConnectionPoolRuntimeException;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum ConnectionPoolImpl implements ConnectionPool {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(ConnectionPoolImpl.class);

    private static final String POOL_NOT_INITIALIZED = "Pool not initialized";
    private static final String CANT_MAKE_AUTO_COMMIT_TRUE = "Error on setting auto commit true";

    private static final int POOL_CAPACITY = 15;
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "url";

    private static final boolean AUTO_COMMIT_TRUE = true;

    private final BlockingQueue<ConnectionProxy> availableConnections;
    private final BlockingQueue<ConnectionProxy> unavailableConnections;
    private boolean isPoolAlreadyInitialized;
    private String url;

    ConnectionPoolImpl() {
        availableConnections = new ArrayBlockingQueue<>(POOL_CAPACITY);
        unavailableConnections = new ArrayBlockingQueue<>(POOL_CAPACITY);
        isPoolAlreadyInitialized = false;
    }

    /**
     * Connection pool initialization
     * @throws NoJDBCDriverException     while no driver
     * @throws NoJDBCPropertiesException while no properties file
     */
    public synchronized void initPool() throws NoJDBCDriverException, NoJDBCPropertiesException {
        if (!isPoolAlreadyInitialized) {
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
                isPoolAlreadyInitialized = true;
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
     * Returns true if pool initialized, false otherwise
     * @return is pool initiated
     */
    public boolean isPoolInitialized() {
        return isPoolAlreadyInitialized;
    }

    /**
     * Returns available connection from pool (auto commit true)
     * @return Connection ready to use
     */
    @Override
    public ConnectionProxy getConnection() {
        if (!isPoolAlreadyInitialized) {
            throw new ConnectionPoolRuntimeException(POOL_NOT_INITIALIZED);
        }
        try {
            ConnectionProxy connection = availableConnections.take();
            unavailableConnections.add(connection);
            connection.setAutoCommit(AUTO_COMMIT_TRUE);
            return connection;
        } catch (InterruptedException e) {
            LOG.warn(e);
            Thread.currentThread().interrupt();
            throw new ConnectionPoolRuntimeException(e);
        } catch (SQLException e) {
            LOG.warn(e);
            throw new ConnectionPoolRuntimeException(CANT_MAKE_AUTO_COMMIT_TRUE);
        }
    }

    /**
     * Method for returning connection to pool
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
     * Connection pool de-initialization
     */
    public void deInitPool() {
        if (isPoolAlreadyInitialized) {
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

            isPoolAlreadyInitialized = false;
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
