package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOption;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Optional;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowUserPage implements Command {

    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.valueOf(0);

    private UserService userService;
    private SubscriptionService subscriptionService;

    public ShowUserPage() {
        userService = ServiceFactory.getInstance().getUserService();
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOption.MAIN);

        try {
            String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            Optional<User> optionalUser = userService.getUserByLogin(login);
            BigDecimal userBalance;
            if (optionalUser.isPresent()) {
                userBalance = optionalUser.get().getMoney();
            } else {
                userBalance = DEFAULT_BALANCE;
            }
            int subscriptionsCount = subscriptionService.getAllSubscriptionsByUserLogin(login).size();
            req.setAttribute(Attribute.USER_BALANCE.getValue(), userBalance);
            req.setAttribute(Attribute.SUBSCRIPTIONS_COUNT.getValue(), subscriptionsCount);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.USERS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}
