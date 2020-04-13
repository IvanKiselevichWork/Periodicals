package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoConnectionAvailableException;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public interface ConnectionPool {
    Connection getConnection();
    Connection getConnection(long waitingDuration, TimeUnit timeUnit) throws NoConnectionAvailableException;
}
