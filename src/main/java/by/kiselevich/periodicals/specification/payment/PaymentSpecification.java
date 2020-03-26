package by.kiselevich.periodicals.specification.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentSpecification extends Specification<Payment> {

    String ID = "id";
    String USER_ID = "user_id";
    String TYPE_ID = "type_id";
    String DATE = "date";
    String AMOUNT = "amount";
    String SUBSCRIPTION_ID = "subscription_id";

    //todo time
    default List<Payment> getPaymentsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        while (resultSet.next()) {
            payments.add(new Payment(
                    resultSet.getInt(ID),
                    resultSet.getInt(USER_ID),
                    resultSet.getInt(TYPE_ID),
                    resultSet.getDate(DATE).toLocalDate(),
                    resultSet.getBigDecimal(AMOUNT),
                    resultSet.getInt(SUBSCRIPTION_ID)
            ));
        }
        return payments;
    }
}
