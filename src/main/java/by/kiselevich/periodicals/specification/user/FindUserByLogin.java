package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.repository.Repository;

import java.util.List;

public class FindUserByLogin implements UserSpecification {
    @Override
    public List<User> query(Repository<User> repository) {
        return null; ///todo
    }
}
