package by.kiselevich.periodicals.pool;

import java.sql.Connection;

/**
 * Class for working with {@link Connection} in transactions.
 * Method {@code close()} do nothing, so it can be used in {@code try-with-resources} statements
 */
public class TransactionConnectionProxy extends ConnectionProxy {

    public TransactionConnectionProxy(Connection connection) {
        super(connection);
    }

    public ConnectionProxy getInnerConnection() {
        return (ConnectionProxy) connection;
    }

    @Override
    public void close() {
        // do nothing to prevent auto closing in try-with-resources statement
    }
}
