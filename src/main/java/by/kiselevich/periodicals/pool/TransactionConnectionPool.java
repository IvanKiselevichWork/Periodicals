package by.kiselevich.periodicals.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionConnectionPool implements ConnectionPool {

    private static final Logger LOG = LogManager.getLogger(TransactionConnectionPool.class);

    private TransactionConnectionProxy connection;

    public TransactionConnectionPool(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            LOG.warn(e);
        }
        this.connection = new TransactionConnectionProxy(connection);
    }

    @Override
    public TransactionConnectionProxy getConnection() {
        return connection;
    }

}
