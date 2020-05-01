package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.factory.SubscriptionToItsStatusMapFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowSubscriptionsCommand implements Command {

    private final SubscriptionService subscriptionService;

    public ShowSubscriptionsCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), DashboardPageOptionCommand.SUBSCRIPTIONS);
        try {
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptions();
            Map<Subscription, String> subscriptionAndStatusMap = SubscriptionToItsStatusMapFactory.getSubscriptionAndStatusMap(subscriptionList);
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), subscriptionAndStatusMap);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
