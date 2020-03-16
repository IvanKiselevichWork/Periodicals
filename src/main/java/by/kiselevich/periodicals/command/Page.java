package by.kiselevich.periodicals.command;

public enum Page {
    HOME("/jsp/home.jsp"),
    WRONG_REQUEST("/jsp/wrong_request.jsp"),
    EMPTY_PAGE("");

    private String path;

    Page(String page) {
        this.path = page;
    }

    public String getPath() {
        return path;
    }
}
