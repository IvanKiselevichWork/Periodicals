package by.kiselevich.periodicals.specification.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindAllPayments implements PaymentSpecification {

    private static final String FIND_ALL_PAYMENTS = "select id, user_id, type_id, date, amount, subscription_id from payment";

    @Override
    public List<Payment> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Payment> payments;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_PAYMENTS);
            resultSet = statement.executeQuery();
            payments = getPaymentsFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return payments;
    }
}
