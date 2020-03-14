package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.repository.Repository;

import java.util.List;

public interface Specification<T, S extends Repository<T>> {
    List<T> query(S repository);
}
