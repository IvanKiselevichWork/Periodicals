package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolImplTest {

    private ConnectionPoolImpl connectionPoolImpl;

    @Before
    public void init() throws NoJDBCDriverException, NoJDBCPropertiesException {
        connectionPoolImpl = ConnectionPoolImpl.INSTANCE;
        connectionPoolImpl.initPool();
    }

    @Test
    public void testConnectionPool() throws SQLException {
        try (Connection connection = connectionPoolImpl.getConnection()) {
            Assert.assertNotNull(connection);
        }
    }

    @After
    public void deInit() {
        connectionPoolImpl.deInitPool();
    }
}