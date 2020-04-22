package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class RefillBalanceCommand implements Command {

    private UserService userService;

    public RefillBalanceCommand() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.AMOUNT.getValue())));
            userService.refillBalance(login, amount);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            writeMessageToResponse(resp, message);
        } catch (NumberFormatException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), ResourceBundleMessages.INVALID_REFILL_AMOUNT.getKey());
            writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
