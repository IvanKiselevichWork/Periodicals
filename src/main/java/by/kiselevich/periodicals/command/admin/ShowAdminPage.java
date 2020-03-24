package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAdminPage implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo service
        return Page.ADMIN_PAGE;
    }
}
