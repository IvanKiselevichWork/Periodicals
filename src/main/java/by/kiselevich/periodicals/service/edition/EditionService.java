package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

/**
 * Service to work with {@link Edition}
 */
public interface EditionService {

    /**
     * Adds {@link Edition} to data source
     * @param edition {@link Edition} to add
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void add(Edition edition) throws ServiceException;

    /**
     * Updates {@link Edition} in data source
     * @param edition edition {@link Edition} to update
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void update(Edition edition) throws ServiceException;

    /**
     * Returns all {@link Edition} from data source
     * @return {@code List<Edition>} with all editions
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Edition> getAllEditions() throws ServiceException;

    /**
     * Returns not blocked {@link Edition} with specified {@code name}, {@code typeId}, {@code themeId}
     * @param name {@link Edition} {@code name}
     * @param typeId {@link Edition} {@code editionType}
     * @param themeId {@link Edition} {@code editionTheme}
     * @return {@code List<Edition>}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, Integer typeId, Integer themeId) throws ServiceException;

    /**
     * Returns {@link Edition} from data source by its {@code id}
     * @param editionId {@link Edition} {@code id}
     * @param findNotBlockedEditionsOnly if true - find only not-blocked {@code Edition}
     * @return {@link Edition}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    Edition getEditionById(int editionId, boolean findNotBlockedEditionsOnly) throws ServiceException;

    /**
     * Block {@code Edition} by its {@code id}
     * @param id {@code Edition} {@code id}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void blockEdition(int id) throws ServiceException;

    /**
     * Unblock {@code Edition} by its {@code id}
     * @param id {@code Edition} {@code id}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void unblockEdition(int id) throws ServiceException;
}
