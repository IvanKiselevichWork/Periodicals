package by.kiselevich.periodicals.repository.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface EditionRepository extends Repository<Edition> {

    /**
     * Adds {@code Edition} to data source
     * @param edition {@link Edition} to add
     * @throws RepositoryException if error occurs
     */
    void add(Edition edition) throws RepositoryException;

    /**
     * Updates {@code Edition} in data source
     * @param edition {@link Edition} to update
     * @throws RepositoryException if error occurs
     */
    void update(Edition edition) throws RepositoryException;

    /**
     * Blocks {@code Edition}
     * @param id {@link Edition} {@code id}
     * @throws RepositoryException if error occurs
     */
    void block(int id) throws RepositoryException;

    /**
     * Unblocks {@code Edition}
     * @param id {@link Edition} {@code id}
     * @throws RepositoryException if error occurs
     */
    void unblock(int id) throws RepositoryException;
}
