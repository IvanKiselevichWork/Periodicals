package by.kiselevich.periodicals.pool;

import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    static final Logger LOG = LogManager.getLogger(ConnectionPoolTest.class);

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

    //todo delete this test on release
    //@Test
    public void ConnectionPoolThreadsTest() {
        LOG.trace("threads test started");
        Thread[] threadsInit = new Thread[10];
        Thread[] threadsDeInit = new Thread[10];
        Thread thread;
        for (int i = 0; i < 10; i++) {
            thread = new PoolTesterInit();
            thread.start();
            threadsInit[i] = thread;

            thread = new PoolTesterDeInit();
            thread.start();
            threadsDeInit[i] = thread;
        }
        try {
            for (Thread thread1 : threadsInit) {
                thread1.join();
            }
            for (Thread thread1 : threadsDeInit) {
                thread1.join();
            }
        } catch (InterruptedException e) {
            LOG.error(e);
        }
        ConnectionPool.INSTANCE.deInitPool();
        LOG.trace("threads test ended");
        Assert.assertTrue(true);
    }
}

class PoolTesterInit extends Thread {

    @Override
    public void run() {
        try {
            ConnectionPoolTest.LOG.trace("init start : {}", getName());
            ConnectionPool.INSTANCE.initPool();
            ConnectionPoolTest.LOG.trace("init end : {}", getName());
        } catch (NoJDBCPropertiesException | NoJDBCDriverException e) {
            ConnectionPoolTest.LOG.error(e);
        }
    }
}

class PoolTesterDeInit extends Thread {
    @Override
    public void run() {
        ConnectionPoolTest.LOG.trace("deInit start : {}", getName());
        ConnectionPool.INSTANCE.deInitPool();
        ConnectionPoolTest.LOG.trace("deInit end : {}", getName());
    }
}
