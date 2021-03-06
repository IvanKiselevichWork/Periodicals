package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class UnblockUserCommand implements Command {

    private final UserService userService;

    public UnblockUserCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            userService.unblockUser(id);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            HttpUtil.writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
