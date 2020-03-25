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
    String TYPE = "type";
    String THEME_ID = "theme_id";
    String PERIODICITY_PER_YEAR = "periodicity_per_year";
    String MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS = "minimum_subscription_period_in_months";
    String PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD = "price_for_minimum_subscription_period";


    default List<Edition> getEditionFromResultSet(ResultSet resultSet) throws SQLException {
        List<Edition> editions = new ArrayList<>();
        while (resultSet.next()) {
            editions.add(new Edition(
                    resultSet.getInt(ID),
                    resultSet.getString(NAME),
                    resultSet.getString(TYPE),
                    resultSet.getInt(THEME_ID),
                    resultSet.getInt(PERIODICITY_PER_YEAR),
                    resultSet.getInt(MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS),
                    resultSet.getBigDecimal(PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD)
            ));
        }
        return editions;
    }
}
