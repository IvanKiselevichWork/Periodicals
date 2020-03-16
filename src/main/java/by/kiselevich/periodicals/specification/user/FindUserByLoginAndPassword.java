package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.repository.Repository;

import java.util.List;

public class FindUserByLoginAndPassword implements UserSpecification {

    public FindUserByLoginAndPassword(String login, char[] password) {
        //todo
    }

    @Override
    public List<User> query(Repository<User> repository) {
        return null; ///todo
    }
}
