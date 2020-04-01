package by.kiselevich.periodicals.specification.theme;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.Theme;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ThemeSpecification extends Specification<Theme> {

    String ID = "id";
    String TITLE = "title";

    default List<Theme> getThemeFromResultSet(ResultSet resultSet) throws SQLException {
        List<Theme> themes = new ArrayList<>();
        while (resultSet.next()) {
            themes.add(new Theme.ThemeBuilder()
                    .id(resultSet.getInt(ID))
                    .title(resultSet.getString(TITLE))
                    .build()
            );
        }
        return themes;
    }
}
