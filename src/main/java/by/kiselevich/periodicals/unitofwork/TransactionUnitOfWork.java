package by.kiselevich.periodicals.unitofwork;

import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.pool.TransactionConnectionPool;
import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.repository.payment.PaymentRepositoryImpl;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepository;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepositoryImpl;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.repository.user.UserRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUnitOfWork {

    private static final Logger LOG = LogManager.getLogger(TransactionUnitOfWork.class);

    private ConnectionPool connectionPool;

    private SubscriptionRepository subscriptionRepository;
    private PaymentRepository paymentRepository;
    private UserRepository userRepository;

    public TransactionUnitOfWork() {
        connectionPool = new TransactionConnectionPool(ConnectionPoolImpl.INSTANCE.getConnection());
        subscriptionRepository = new SubscriptionRepositoryImpl(connectionPool);
        paymentRepository = new PaymentRepositoryImpl(connectionPool);
        userRepository = new UserRepositoryImpl(connectionPool);
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void commit() {
        Connection connection = connectionPool.getConnection();
        try {
            connection.commit();
            ConnectionPoolImpl.INSTANCE.returnConnection((ConnectionProxy) connection);
        } catch (SQLException e) {
            LOG.warn(e);
        }
    }

    public void rollback() {
        Connection connection = connectionPool.getConnection();
        try {
            connection.rollback();
            ConnectionPoolImpl.INSTANCE.returnConnection((ConnectionProxy) connection);
        } catch (SQLException e) {
            LOG.warn(e);
        }
    }
}
