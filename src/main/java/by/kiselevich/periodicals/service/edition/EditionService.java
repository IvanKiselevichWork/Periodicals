package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionService {

    Edition add(Edition edition) throws ServiceException;

    List<Edition> getAllEditions() throws ServiceException;

    List<Edition> getEditionsByNameAndTypeIdAndThemeId(String name, int typeId, int themeId) throws ServiceException;

    List<Edition> getEditionsByNameAndTypeId(String name, int typeId) throws ServiceException;

    List<Edition> getEditionsByNameAndThemeId(String name, int themeId) throws ServiceException;

    List<Edition> getEditionsByName(String name) throws ServiceException;
}
