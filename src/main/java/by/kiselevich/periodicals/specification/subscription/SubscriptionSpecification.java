package by.kiselevich.periodicals.specification.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SubscriptionSpecification extends Specification<Subscription> {

    String ID = "id";
    String EDITION_ID = "edition_id";
    String SUBSCRIPTION_START_DATE = "subscription_start_date";
    String SUBSCRIPTION_END_DATE = "subscription_end_date";
    String USER_ID = "user_id";
    String IS_PAID = "is_paid";

    //todo time
    default List<Subscription> getSubscriptionFromResultSet(ResultSet resultSet) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        while (resultSet.next()) {
            subscriptions.add(new Subscription(
                    resultSet.getInt(ID),
                    resultSet.getInt(EDITION_ID),
                    resultSet.getTimestamp(SUBSCRIPTION_START_DATE),
                    resultSet.getTimestamp(SUBSCRIPTION_END_DATE),
                    resultSet.getInt(USER_ID),
                    resultSet.getBoolean(IS_PAID)
            ));
        }
        return subscriptions;
    }
}
