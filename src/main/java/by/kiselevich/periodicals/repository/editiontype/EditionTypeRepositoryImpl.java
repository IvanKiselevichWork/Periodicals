package by.kiselevich.periodicals.repository.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public class EditionTypeRepositoryImpl implements EditionTypeRepository {

    @Override
    public void add(EditionType editionType) throws RepositoryException {
        //todo
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(EditionType editionType) throws RepositoryException {
        //todo
    }

    @Override
    public List<EditionType> query(Specification<EditionType> specification) throws RepositoryException {
        return specification.query();
    }
}
