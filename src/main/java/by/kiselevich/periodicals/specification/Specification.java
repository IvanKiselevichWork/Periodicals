package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.exception.RepositoryException;

import java.util.List;

public interface Specification<T> {
    List<T> query() throws RepositoryException;
}
