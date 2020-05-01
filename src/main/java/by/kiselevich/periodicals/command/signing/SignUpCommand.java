package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.mail.MailService;
import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class SignUpCommand implements Command {

    private final UserService userService;
    private final MailService mailService;

    public SignUpCommand() {
        userService = ServiceFactory.getInstance().getUserService();
        mailService = ServiceFactory.getInstance().getMailService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            User user = createUserFromRequest(req);
            user = userService.signUp(user);
            mailService.sendRegistrationLetter(user);
            req.getSession().setAttribute(Attribute.USER_TYPE.getValue(), UserType.USER);
            req.getSession().setAttribute(Attribute.LOGIN.getValue(), user.getLogin());
            req.getSession().setAttribute(Attribute.FULL_NAME.getValue(), user.getFullName());
            return Page.HOME_PAGE;
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }

    private User createUserFromRequest(HttpServletRequest req) {
        String login = req.getParameter(JspParameter.LOGIN.getValue());
        String password = req.getParameter(JspParameter.PASSWORD.getValue());
        String fullName = req.getParameter(JspParameter.FULL_NAME.getValue());
        fullName = HttpUtil.parseToPreventXss(fullName);
        String email = req.getParameter(JspParameter.EMAIL.getValue());
        return new User.UserBuilder()
                .login(login)
                .password(password)
                .fullName(fullName)
                .email(email)
                .build();
    }
}
