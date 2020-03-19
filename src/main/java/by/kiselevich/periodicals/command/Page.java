package by.kiselevich.periodicals.command;

public enum Page {
    HOME("/jsp/home.jsp"),
    WRONG_REQUEST("/jsp/wrong_request.jsp"),
    APP_FAILURE("/jsp/app_failure.jsp"),
    EMPTY_PAGE("");

    private String path;

    Page(String page) {
        this.path = page;
    }

    public String getPath() {
        return path;
    }
}
