package by.kiselevich.periodicals.specification.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.SpecificationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindEditionsByNameAndTypeIdAndThemeId extends SpecificationUtil implements EditionSpecification {

    private static final String FIND_EDITIONS_BY_NAME_AND_TYPE_ID_AND_THEME_ID = "select * from edition inner join edition_theme on edition.theme_id = edition_theme.id inner join edition_type on edition.type_id = edition_type.id where name like ? and edition_type.id = ? and edition_theme.id = ?";

    private String name;
    private int typeId;
    private int themeId;

    public FindEditionsByNameAndTypeIdAndThemeId(String name, int typeId, int themeId) {
        this.name = name;
        this.typeId = typeId;
        this.themeId = themeId;
    }

    @Override
    public List<Edition> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Edition> editions = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_EDITIONS_BY_NAME_AND_TYPE_ID_AND_THEME_ID);
            statement.setString(1, name);
            statement.setInt(2, typeId);
            statement.setInt(3, themeId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                editions.add(getEditionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return editions;
    }
}
