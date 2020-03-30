package by.kiselevich.periodicals.command;

public enum UserRole {
    GUEST,
    USER,
    ADMIN;

    private static final int ADMIN_ROLE_ID = 1;
    private static final int USER_ROLE_ID = 2;

    public static UserRole getUserRole(String value) {
        UserRole userRole = null;
        try {
            userRole = UserRole.valueOf(value);
        } catch (IllegalArgumentException ignored) {
            userRole = GUEST;
        }
        return userRole;
    }

    public static UserRole getUserRoleById(int id) {
        if (id == ADMIN_ROLE_ID) {
            return ADMIN;
        }
        if (id == USER_ROLE_ID) {
            return  USER;
        }
        return GUEST;
    }
}
