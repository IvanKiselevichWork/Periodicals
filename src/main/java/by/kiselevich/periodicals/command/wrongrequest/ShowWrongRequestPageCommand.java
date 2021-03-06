package by.kiselevich.periodicals.command.wrongrequest;

import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowWrongRequestPageCommand implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        return Page.WRONG_REQUEST;
    }
}
