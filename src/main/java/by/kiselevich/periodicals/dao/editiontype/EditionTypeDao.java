package by.kiselevich.periodicals.dao.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface EditionTypeDao {

    /**
     * Get all {@link EditionType} from data source
     * @return {@code List} of {@link EditionType}
     * @throws DaoException if error occurs
     */
    List<EditionType> getAllEditionTypes() throws DaoException;

    /**
     * Get {@link EditionType} by {@code id} from data source
     * @param id {@link EditionType} {@code id}
     * @return {@link EditionType} with given {@code id} or {@code null}
     * @throws DaoException if error occurs
     */
    EditionType getEditionTypeById(int id) throws DaoException;
}
