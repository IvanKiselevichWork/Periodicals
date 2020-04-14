package by.kiselevich.periodicals.command.home;

import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowHomePageCommand implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        return Page.HOME_PAGE;
    }
}
