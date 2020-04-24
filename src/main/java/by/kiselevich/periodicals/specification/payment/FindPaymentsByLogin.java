package by.kiselevich.periodicals.specification.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.SpecificationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindPaymentsByLogin extends SpecificationUtil implements PaymentSpecification {

    private static final String FIND_PAYMENTS_BY_LOGIN = "select * from payment inner join payment_type on payment.type_id = payment_type.id left join subscription on payment.subscription_id = subscription.id left join edition on subscription.edition_id = edition.id inner join user on payment.user_id = user.id left join edition_theme on edition.theme_id = edition_theme.id left join edition_type on edition.type_id = edition_type.id inner join user_role on user.role_id = user_role.id where user.login = ?";

    private final String userLogin;

    public FindPaymentsByLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public List<Payment> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_PAYMENTS_BY_LOGIN);
            statement.setString(1, userLogin);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(getPaymentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return payments;
    }
}
