package by.kiselevich.periodicals.repository.theme;

import by.kiselevich.periodicals.entity.Theme;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public class ThemeRepositoryImpl implements ThemeRepository {

    @Override
    public void add(Theme theme) throws RepositoryException {
        //todo
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(Theme theme) throws RepositoryException {
        //todo
    }

    @Override
    public List<Theme> query(Specification<Theme> specification) throws RepositoryException {
        return specification.query();
    }
}
