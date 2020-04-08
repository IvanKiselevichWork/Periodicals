package by.kiselevich.periodicals.service.editiontheme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionThemeService {

    List<EditionTheme> getAllThemes() throws ServiceException;

    List<EditionTheme> getThemeById(int id) throws ServiceException;
}
