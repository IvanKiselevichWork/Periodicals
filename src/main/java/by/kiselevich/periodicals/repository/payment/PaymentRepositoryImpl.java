package by.kiselevich.periodicals.repository.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.*;
import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepository {

    private static final String ADD_PAYMENT = "insert into payment(user_id, type_id, date, amount, subscription_id) values(?, ?, ?, ?, ?)";

    private static final String PAYMENT_NOT_ADDED_MESSAGE = "Payment has not been added";

    private ConnectionPool connectionPool;

    public PaymentRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Payment payment) throws RepositoryException {
        ResultSet generatedId = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, payment.getUser().getId());
            statement.setInt(2, payment.getPaymentType().getId());
            statement.setTimestamp(3, payment.getDate());
            statement.setBigDecimal(4, payment.getAmount());
            statement.setInt(5, payment.getSubscription().getId());
            int updatedRowCount = statement.executeUpdate();
            boolean isPaymentAdded = false;
            if (updatedRowCount == 1) {
                generatedId = statement.getGeneratedKeys();
                if (generatedId.next()) {
                    payment.setId(generatedId.getInt(1));
                    isPaymentAdded = true;
                }
            }
            if (!isPaymentAdded) {
                throw new RepositoryException(PAYMENT_NOT_ADDED_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(generatedId);
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(Payment payment) throws RepositoryException {
        //todo
    }

    @Override
    public List<Payment> query(Specification<Payment> specification) throws RepositoryException {
        return specification.query();
    }
}
