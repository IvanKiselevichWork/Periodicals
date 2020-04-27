package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public interface Repository<T> {

    /**
     * Execute {@code query} method {@link Specification} interface
     * @param specification {@link Specification} to execute
     * @return {@link List} with results
     * @throws RepositoryException if error occurs
     */
    List<T> query(Specification<T> specification) throws RepositoryException;
}
