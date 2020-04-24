package by.kiselevich.periodicals.unitofwork;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.*;
import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.repository.payment.PaymentRepositoryImpl;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepository;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepositoryImpl;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.repository.user.UserRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionUnitOfWork {

    private static final Logger LOG = LogManager.getLogger(TransactionUnitOfWork.class);

    private final TransactionConnectionPool connectionPool;

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

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

    public void commit() throws RepositoryException {
        TransactionConnectionProxy connection = connectionPool.getConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.INSTANCE.returnConnection(connection.getInnerConnection());
        }
    }

    public void rollback() {
        TransactionConnectionProxy connection = connectionPool.getConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.warn(e);
        } finally {
            ConnectionPoolImpl.INSTANCE.returnConnection(connection.getInnerConnection());
        }
    }
}
