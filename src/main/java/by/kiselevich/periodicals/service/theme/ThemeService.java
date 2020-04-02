package by.kiselevich.periodicals.service.theme;

import by.kiselevich.periodicals.entity.Theme;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface ThemeService {

    List<Theme> getAllThemes() throws ServiceException;

    List<Theme> getThemeById(int id) throws ServiceException;
}
