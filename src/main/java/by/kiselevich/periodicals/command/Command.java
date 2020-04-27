package by.kiselevich.periodicals.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    /**
     * Execute command and return {@code Page} result to controller
     * @param req {@link HttpServletRequest} request
     * @param resp {@link HttpServletResponse} response
     * @return {@link Page} result to dispatch
     */
    Page execute(HttpServletRequest req, HttpServletResponse resp);
}
