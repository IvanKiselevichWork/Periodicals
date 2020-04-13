package by.kiselevich.periodicals.repository.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public class ThemeRepositoryImpl implements ThemeRepository {

    private ConnectionPool connectionPool;

    public ThemeRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(EditionTheme editionTheme) throws RepositoryException {
        //todo
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(EditionTheme editionTheme) throws RepositoryException {
        //todo
    }

    @Override
    public List<EditionTheme> query(Specification<EditionTheme> specification) throws RepositoryException {
        return specification.query();
    }
}
