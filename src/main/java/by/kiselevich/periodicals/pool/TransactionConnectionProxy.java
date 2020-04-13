package by.kiselevich.periodicals.pool;

import java.sql.Connection;

public class TransactionConnectionProxy extends ConnectionProxy {

    public TransactionConnectionProxy(Connection connection) {
        super(connection);
    }

    @Override
    public void close() {
        // do nothing
    }
}
