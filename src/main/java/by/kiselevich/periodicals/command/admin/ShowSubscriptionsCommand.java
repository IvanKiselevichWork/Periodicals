package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.factory.SubscriptionToItsStatusMapFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowSubscriptionsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ShowSubscriptionsCommand.class);

    private static final int DEFAULT_RECORD_COUNT_PER_PAGE = 50;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private final SubscriptionService subscriptionService;

    public ShowSubscriptionsCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), DashboardPageOptionCommand.SUBSCRIPTIONS);
        try {
            int subscriptionCount = subscriptionService.getAllSubscriptionCount();
            int pages = subscriptionCount / DEFAULT_RECORD_COUNT_PER_PAGE + (subscriptionCount % DEFAULT_RECORD_COUNT_PER_PAGE == 0 ? 0 : 1);
            req.setAttribute(Attribute.PAGES.getValue(), pages);
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptions();
            int pageNumber = getPageNumberFromRequest(req);
            int fromIndex = (pageNumber - 1) * DEFAULT_RECORD_COUNT_PER_PAGE;
            if (fromIndex < 0 || fromIndex >= subscriptionCount) {
                LOG.warn("Illegal fromIndex value in ShowSubscriptionsCommand");
                return Page.WRONG_REQUEST;
            }
            int toIndex =  pageNumber * DEFAULT_RECORD_COUNT_PER_PAGE;
            if (toIndex <= 0) {
                LOG.warn("Illegal toIndex value in ShowSubscriptionsCommand");
                return Page.WRONG_REQUEST;
            }
            if (toIndex > subscriptionCount) {
                toIndex = subscriptionCount;
            }
            subscriptionList = subscriptionList.subList(fromIndex, toIndex);
            Map<Subscription, String> subscriptionAndStatusMap = SubscriptionToItsStatusMapFactory.getSubscriptionAndStatusMap(subscriptionList);
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), subscriptionAndStatusMap);
            req.setAttribute(Attribute.SUBSCRIPTIONS_COUNT.getValue(), subscriptionCount);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }

    private int getPageNumberFromRequest(HttpServletRequest req) {
        String pageNumberString = req.getParameter(JspParameter.PAGE.getValue());
        try {
            return Integer.parseInt(pageNumberString);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }
}
