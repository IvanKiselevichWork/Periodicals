package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class BlockUser implements Command {

    private UserService userService;

    public BlockUser() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            userService.blockUser(id);
            Command showUsers = CommandProvider.getInstance().getCommand(CommandName.SHOW_USERS);
            return showUsers.execute(req, resp);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            HttpUtil.writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }
}