package by.kiselevich.periodicals.specification.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EditionTypeSpecification extends Specification<EditionType> {

    String ID = "id";
    String TYPE = "type";

    default List<EditionType> getEditionsTypesFromResultSet(ResultSet resultSet) throws SQLException {
        List<EditionType> editionTypes = new ArrayList<>();
        while (resultSet.next()) {
            editionTypes.add(new EditionType.EditionTypeBuilder()
                    .id(resultSet.getInt(ID))
                    .type(resultSet.getString(TYPE))
                    .build()
            );
        }
        return editionTypes;
    }
}
