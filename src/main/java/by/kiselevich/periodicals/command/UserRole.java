package by.kiselevich.periodicals.command;

public enum UserRole {
    GUEST,
    USER;

    public static UserRole getUserRole(String value) {
        UserRole userRole = null;
        try {
            userRole = UserRole.valueOf(value);
        } catch (IllegalArgumentException ignored) {
            userRole = GUEST;
        }
        return userRole;
    }
}
