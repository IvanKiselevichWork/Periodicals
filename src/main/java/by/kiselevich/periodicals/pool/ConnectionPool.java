package by.kiselevich.periodicals.pool;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
}
