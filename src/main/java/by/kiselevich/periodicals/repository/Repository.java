package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T t) throws RepositoryException;

    void remove(int id) throws RepositoryException;

    void update(T t) throws RepositoryException;

    List<T> query(Specification<T, Repository<T>> specification) throws RepositoryException;
}
