package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public interface Repository<T> {

    List<T> query(Specification<T> specification) throws RepositoryException;
}
