package by.kiselevich.periodicals.specification.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ThemeSpecification extends Specification<EditionTheme> {

    String ID = "id";
    String TITLE = "title";

    default List<EditionTheme> getThemeFromResultSet(ResultSet resultSet) throws SQLException {
        List<EditionTheme> editionThemes = new ArrayList<>();
        while (resultSet.next()) {
            editionThemes.add(new EditionTheme.EditionThemeBuilder()
                    .id(resultSet.getInt(ID))
                    .title(resultSet.getString(TITLE))
                    .build()
            );
        }
        return editionThemes;
    }
}
