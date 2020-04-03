package by.kiselevich.periodicals.service.theme;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.theme.ThemeRepository;
import by.kiselevich.periodicals.specification.theme.FindAllThemes;
import by.kiselevich.periodicals.specification.theme.FindThemeById;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {

    private static final Logger LOG = LogManager.getLogger(ThemeServiceImpl.class);

    private ThemeRepository themeRepository;

    public ThemeServiceImpl() {
        themeRepository = RepositoryFactory.getInstance().getThemeRepository();
    }

    @Override
    public List<EditionTheme> getAllThemes() throws ServiceException {
        try {
            return themeRepository.query(new FindAllThemes());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<EditionTheme> getThemeById(int id) throws ServiceException {
        try {
            return themeRepository.query(new FindThemeById(id));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
