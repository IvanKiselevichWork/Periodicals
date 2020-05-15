package by.kiselevich.periodicals.dao.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface EditionDao {

    /**
     * Adds {@link Edition} to data source
     * @param edition {@link Edition} to add
     * @throws DaoException if error occurs
     */
    void add(Edition edition) throws DaoException;

    /**
     * Updates {@link Edition} in data source
     * @param edition {@link Edition} to update
     * @throws DaoException if error occurs
     */
    void update(Edition edition) throws DaoException;

    /**
     * Blocks {@link Edition}
     * @param id {@link Edition} {@code id}
     * @throws DaoException if error occurs
     */
    void block(int id) throws DaoException;

    /**
     * Unblocks {@link Edition}
     * @param id {@link Edition} {@code id}
     * @throws DaoException if error occurs
     */
    void unblock(int id) throws DaoException;

    /**
     * Get all {@link Edition} from data source
     * @return {@code List} of {@link Edition}
     * @throws DaoException if error occurs
     */
    List<Edition> getAllEditions() throws DaoException;

    /**
     * Get {@link Edition} by {@code id} from data source
     * @param id {@link Edition} {@code id}
     * @return {@link Edition} with given {@code id}
     * @throws DaoException if error occurs
     */
    Edition getEditionById(int id, boolean findNotBlockedEditionsOnly) throws DaoException;

    /**
     * Get all {@link Edition} by {@code name}, {@link EditionType} {@code id} and {@link EditionTheme} {@code id} from data source. <br>
     * In {@code name} replacing exclamation mark, percent sign, underscore symbol and place in {@code %%%s%%} pattern (if not null). <br>
     * {@link EditionType} {@code id} and {@link EditionTheme} {@code id} also counts only if not null
     * @param name {@link Edition} {@code name} (or part)
     * @param typeId {@link EditionType} {@code id}
     * @param themeId {@link EditionTheme} {@code id}
     * @param getBlocked is return blocked {@link Edition}
     * @return {@code List} of {@link Edition}
     * @throws DaoException if error occurs
     */
    List<Edition> getEditionsByNameAndTypeAndThemeAndBlockage(String name, Integer typeId, Integer themeId, Boolean getBlocked) throws DaoException;
}
