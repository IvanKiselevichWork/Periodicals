package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowUserSubscriptionsCommand implements Command {

    private SubscriptionService subscriptionService;

    public ShowUserSubscriptionsCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.SUBSCRIPTIONS);
        try {
            String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptionsByUserLogin(login);
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), subscriptionList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.USERS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}