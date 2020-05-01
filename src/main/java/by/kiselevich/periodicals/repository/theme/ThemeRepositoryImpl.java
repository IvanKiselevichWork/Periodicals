package by.kiselevich.periodicals.repository.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

/**
 * Implementation of {@link ThemeRepository}
 */
public class ThemeRepositoryImpl implements ThemeRepository {

    @Override
    public List<EditionTheme> query(Specification<EditionTheme> specification) throws RepositoryException {
        return specification.query();
    }
}
