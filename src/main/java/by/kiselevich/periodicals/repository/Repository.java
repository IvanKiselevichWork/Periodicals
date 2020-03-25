package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T t) throws RepositoryException;

    void block(int id) throws RepositoryException;

    void unblock(int id) throws RepositoryException;

    void update(T t) throws RepositoryException;

    List<T> query(Specification<T> specification) throws RepositoryException;
}
