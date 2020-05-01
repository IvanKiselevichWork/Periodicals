package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.exception.RepositoryException;

import java.util.List;

/**
 * Base interface for all specification {@link by.kiselevich.periodicals.repository.Repository} work with
 * @param <T> entity specification work with
 */
public interface Specification<T> {
    /**
     * Execute {@code Specification} and return result as {@code List}
     * @return {@code List} result
     * @throws RepositoryException if repository error occurs
     */
    List<T> query() throws RepositoryException;
}
