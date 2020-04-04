package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.UserType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOut implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute(Attribute.USER_TYPE.getValue(), UserType.GUEST);
        req.getSession().setAttribute(Attribute.LOGIN.getValue(), null);
        return Page.HOME_PAGE;
    }
}
