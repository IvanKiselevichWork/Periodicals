package by.kiselevich.periodicals.service.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface ThemeService {

    List<EditionTheme> getAllThemes() throws ServiceException;

    List<EditionTheme> getThemeById(int id) throws ServiceException;
}
