package by.kiselevich.periodicals.pool;

import java.sql.Connection;

public interface ConnectionPool {
    /**
     * Returns {@link Connection} from {@code ConnectionPool}
     * @return available {@link Connection}
     */
    Connection getConnection();
}
