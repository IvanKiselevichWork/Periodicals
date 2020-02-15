package by.kiselevich.periodicals.dao;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DAOException;

public interface UserDAO {

    public User signIn(String login, char[] password) throws DAOException;

    public User signUp(String username, String login, char[] password) throws DAOException;

}
