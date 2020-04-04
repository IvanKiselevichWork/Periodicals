package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowSubscriptions implements Command {

    private SubscriptionService subscriptionService;

    public ShowSubscriptions() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), AdminPageOption.SUBSCRIPTIONS);
        try {
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptions();
            subscriptionList.sort(Comparator.comparing(Subscription::getId));
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), subscriptionList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.SUBSCRIPTIONS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
