package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class SignInCommand implements Command {

    private final UserService userService;

    public SignInCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {

        String login = req.getParameter(JspParameter.LOGIN.getValue());
        String password = req.getParameter(JspParameter.PASSWORD.getValue());
        try {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user = userService.signIn(user);
            req.getSession().setAttribute(Attribute.USER_TYPE.getValue(), UserType.getUserTypeByUser(user));
            req.getSession().setAttribute(Attribute.LOGIN.getValue(), login);
            req.getSession().setAttribute(Attribute.FULL_NAME.getValue(), user.getFullName());
            return Page.HOME_PAGE;
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }
}
