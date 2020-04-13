package by.kiselevich.periodicals.pool;

import java.sql.Connection;

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
