package by.kiselevich.periodicals.specification.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EditionSpecification extends Specification<Edition> {

    String ID = "id";
    String NAME = "name";
    String TYPE_ID = "type_id";
    String THEME_ID = "theme_id";
    String PERIODICITY_PER_YEAR = "periodicity_per_year";
    String MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS = "minimum_subscription_period_in_months";
    String PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD = "price_for_minimum_subscription_period";


    default List<Edition> getEditionFromResultSet(ResultSet resultSet) throws SQLException {
        List<Edition> editions = new ArrayList<>();
        while (resultSet.next()) {
            editions.add(new Edition.EditionBuilder()
                    .id(resultSet.getInt(ID))
                    .name(resultSet.getString(NAME))
                    .typeId(resultSet.getInt(TYPE_ID))
                    .themeId(resultSet.getInt(THEME_ID))
                    .periodicityPerYear(resultSet.getInt(PERIODICITY_PER_YEAR))
                    .minimumSubscriptionPeriodInMonths(resultSet.getInt(MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS))
                    .priceForMinimumSubscriptionPeriod(resultSet.getBigDecimal(PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD))
                    .build()
            );
        }
        return editions;
    }
}
