package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolImplTest {

    private static ConnectionPoolImpl connectionPoolImpl;

    @BeforeClass
    public static void init() throws NoJDBCDriverException, NoJDBCPropertiesException {
        connectionPoolImpl = ConnectionPoolImpl.INSTANCE;
        connectionPoolImpl.initPool();
    }

    @Test
    public void testConnectionPool() throws SQLException {
        try (Connection connection = connectionPoolImpl.getConnection()) {
            Assert.assertNotNull(connection);
        }
    }

    @AfterClass
    public static void deInit() {
        connectionPoolImpl.deInitPool();
    }
}