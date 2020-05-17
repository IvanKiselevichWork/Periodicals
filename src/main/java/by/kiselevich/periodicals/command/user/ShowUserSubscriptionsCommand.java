package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.command.admin.LongListUtil;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.SubscriptionToItsStatusMapFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowUserSubscriptionsCommand extends AbstractUserCommand implements Command {

    private final LongListUtil<Subscription> longListUtil;

    public ShowUserSubscriptionsCommand() {
        longListUtil = new LongListUtil<>();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.SUBSCRIPTIONS);
        try {
            List<Subscription> subscriptionList = getSubscriptionsByUserLoginFromRequest(req);
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