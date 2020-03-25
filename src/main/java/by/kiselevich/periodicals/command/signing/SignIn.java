package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.UserServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class SignIn implements Command {

    private UserService userService;

    public SignIn() {
        userService = UserServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {

        String login = req.getParameter(JspParameter.LOGIN.getValue());
        String password = req.getParameter(JspParameter.PASSWORD.getValue());
        try {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password.toCharArray());
            password = null;
            userService.signIn(user);
            req.getSession().setAttribute(Attribute.USER_ROLE.getValue(), UserRole.getUserRoleById(user.getRoleId()));
            req.getSession().setAttribute(Attribute.LOGIN.getValue(), login);
            return Page.HOME_PAGE;
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }
}
