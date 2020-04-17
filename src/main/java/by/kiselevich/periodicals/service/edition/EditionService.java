package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionService {

    Edition add(Edition edition) throws ServiceException;

    List<Edition> getAllEditions() throws ServiceException;

    List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, int typeId, int themeId) throws ServiceException;

    List<Edition> getNotBlockedEditionsByNameAndTypeId(String name, int typeId) throws ServiceException;

    List<Edition> getNotBlockedEditionsByNameAndThemeId(String name, int themeId) throws ServiceException;

    List<Edition> getNotBlockedEditionsByName(String name) throws ServiceException;

    List<Edition> getEditionsById(int editionId) throws ServiceException;
}
