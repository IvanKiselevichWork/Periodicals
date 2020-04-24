package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionService {

    void add(Edition edition) throws ServiceException;

    void update(Edition edition) throws ServiceException;

    List<Edition> getAllEditions() throws ServiceException;

    List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, Integer typeId, Integer themeId) throws ServiceException;

    List<Edition> getEditionsById(int editionId, boolean findNotBlockedEditionsOnly) throws ServiceException;

    void blockEdition(int id) throws ServiceException;

    void unblockEdition(int id) throws ServiceException;
}
