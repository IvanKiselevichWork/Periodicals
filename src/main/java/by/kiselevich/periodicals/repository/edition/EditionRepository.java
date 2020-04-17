package by.kiselevich.periodicals.repository.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface EditionRepository extends Repository<Edition> {

    void block(int id) throws RepositoryException;

    void unblock(int id) throws RepositoryException;
}
