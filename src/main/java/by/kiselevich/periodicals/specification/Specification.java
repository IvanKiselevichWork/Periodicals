package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.exception.RepositoryException;

import java.util.List;

public interface Specification<T> {
    /**
     * Execute {@code Specification} and return result as {@code List}
     * @return {@code List} result
     * @throws RepositoryException if repository error occurs
     */
    List<T> query() throws RepositoryException;
}
