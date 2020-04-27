package by.kiselevich.periodicals.service.editiontype;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionTypeService {

    /**
     * Returns all {@link EditionType} from data source
     * @return {@link List} with all {@link EditionType}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<EditionType> getAllEditionsTypes() throws ServiceException;

    /**
     * Returns {@link EditionType} from data source by its {@code id}
     * @param id {@link EditionType} {@code id}
     * @return {@link List} with {@link EditionType}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<EditionType> getEditionTypeById(int id) throws ServiceException;
}
