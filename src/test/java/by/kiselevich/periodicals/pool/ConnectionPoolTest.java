package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    private ConnectionPool connectionPool;

    @Before
    public void init() throws NoJDBCDriverException, NoJDBCPropertiesException {
        connectionPool = ConnectionPool.INSTANCE;
        connectionPool.initPool();
    }

    @Test
    public void ConnectionPoolTest1() throws SQLException {
        try (Connection connection = connectionPool.getConnection()) {
            Assert.assertNotNull(connection);
        }
    }

    @After
    public void deInit() {
        connectionPool.deInitPool();
    }
}
