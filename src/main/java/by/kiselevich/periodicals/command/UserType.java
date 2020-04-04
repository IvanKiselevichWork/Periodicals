package by.kiselevich.periodicals.command;

import by.kiselevich.periodicals.entity.User;

public enum  UserType {
    GUEST,
    ADMIN,
    USER;

    public static UserType getUserTypeByUser(User user) {
        return valueOf(user.getRole().getRole().toUpperCase());
    }
}
