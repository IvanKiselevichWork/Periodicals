package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.exception.UserServiceException;
import by.kiselevich.periodicals.factory.UserServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class SignUp implements Command {

    private UserService userService;

    public SignUp() {
        userService = UserServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(Parameter.LOGIN.getValue());
        String passwordString = req.getParameter(Parameter.PASSWORD.getValue());
        try {
            userService.singUp(login, passwordString);
            req.getSession().setAttribute(Attribute.USER_ROLE.getValue(), UserRole.USER);
            req.getSession().setAttribute(Attribute.LOGIN.getValue(), login);
            return Page.HOME;
        } catch (UserServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }


}
