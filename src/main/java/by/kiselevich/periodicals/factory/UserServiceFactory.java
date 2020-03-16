package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.service.user.UserServiceImpl;

public class UserServiceFactory {

    private UserService userService;

    private UserServiceFactory(){
        userService = new UserServiceImpl();
    }

    private static class UserServiceFactoryHolder {
        private static final UserServiceFactory INSTANCE = new UserServiceFactory();
    }

    public static UserServiceFactory getInstance() {
        return UserServiceFactoryHolder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }
}
