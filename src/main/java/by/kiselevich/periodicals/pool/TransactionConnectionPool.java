package by.kiselevich.periodicals.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class TransactionConnectionPool implements ConnectionPool {

    private static final Logger LOG = LogManager.getLogger(TransactionConnectionProxy.class);

    private Connection connection;

    public TransactionConnectionPool(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return getTransactionConnection();
    }

    @Override
    public Connection getConnection(long waitingDuration, TimeUnit timeUnit) {
        return getTransactionConnection();
    }

    private TransactionConnectionProxy getTransactionConnection() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.warn(e);
        }
        return new TransactionConnectionProxy(connection);
    }
}
