package by.kiselevich.periodicals.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface to use in {@link by.kiselevich.periodicals.controller.ControllerServlet}
 * All instances stores in {@link CommandProvider}
 * All commands names stores in {@link CommandName}
 * Command by user's roles stores in {@link CommandNames}
 */
public interface Command {
    /**
     * Execute command and return {@code Page} result to controller
     * @param req {@link HttpServletRequest} request
     * @param resp {@link HttpServletResponse} response
     * @return {@link Page} result to dispatch
     */
    Page execute(HttpServletRequest req, HttpServletResponse resp);
}
