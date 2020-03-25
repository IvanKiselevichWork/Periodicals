package by.kiselevich.periodicals.repository.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EditionRepositoryImpl implements EditionRepository {

    private static final Logger LOG = LogManager.getLogger(EditionRepositoryImpl.class);

    @Override
    public void add(Edition edition) throws RepositoryException {
        //todo
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(Edition edition) throws RepositoryException {
        //todo
    }

    @Override
    public List<Edition> query(Specification<Edition> specification) throws RepositoryException {
        return specification.query();
    }
}
