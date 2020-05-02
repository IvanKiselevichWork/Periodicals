package by.kiselevich.periodicals.repository.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.repository.RepositoryUtil;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.*;
import java.util.List;

/**
 * Implementation of {@link PaymentRepository}
 */
public class PaymentRepositoryImpl extends RepositoryUtil implements PaymentRepository {

    private static final String ADD_PAYMENT = "insert into payment(user_id, type_id, date, amount, subscription_id) values(?, ?, ?, ?, ?)";

    private static final String PAYMENT_NOT_ADDED_MESSAGE = "Payment has not been added";

    private final ConnectionPool connectionPool;

    public PaymentRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Payment payment) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, payment.getUser().getId());
            statement.setInt(2, payment.getPaymentType().getId());
            statement.setTimestamp(3, payment.getDate());
            statement.setBigDecimal(4, payment.getAmount());
            if (payment.getSubscription() != null) {
                statement.setInt(5, payment.getSubscription().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            int generatedId = executeUpdateOneRowAndGetGeneratedId(statement, PAYMENT_NOT_ADDED_MESSAGE);
            payment.setId(generatedId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Payment> query(Specification<Payment> specification) throws RepositoryException {
        return specification.query();
    }
}
