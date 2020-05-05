package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.command.admin.LongListUtil;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.factory.SubscriptionToItsStatusMapFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowUserSubscriptionsCommand implements Command {

    private final SubscriptionService subscriptionService;
    private final LongListUtil<Subscription> longListUtil;

    public ShowUserSubscriptionsCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
        longListUtil = new LongListUtil<>();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.SUBSCRIPTIONS);
        try {
            String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptionsByUserLogin(login);
            subscriptionList = longListUtil.getSubListByPageFromRequest(req, subscriptionList);
            Map<Subscription, String> subscriptionAndStatusMap = SubscriptionToItsStatusMapFactory.getSubscriptionAndStatusMap(subscriptionList);
            Map<Subscription, String> subscriptionAndStatusSortedMap = new TreeMap<>(Comparator.comparing(Subscription::getSubscriptionStartDate).reversed());
            subscriptionAndStatusSortedMap.putAll(subscriptionAndStatusMap);
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), subscriptionAndStatusSortedMap);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}