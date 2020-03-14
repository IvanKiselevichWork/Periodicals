package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T t);

    void remove(T t);

    void update(T t);

    List<T> query(Specification<T, Repository<T>> specification);
}
