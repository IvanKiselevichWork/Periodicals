package by.kiselevich.periodicals.command;

public enum Page {
    HOME_PAGE("/jsp/home.jsp"),
    WRONG_REQUEST("/jsp/wrong_request.jsp"),
    APP_FAILURE("/jsp/app_failure.jsp"),
    ADMIN_PAGE("/jsp/admin.jsp"),
    USER_PAGE("/jsp/user.jsp"),
    EMPTY_PAGE("");

    private final String path;

    Page(String page) {
        this.path = page;
    }

    public String getPath() {
        return path;
    }
}
