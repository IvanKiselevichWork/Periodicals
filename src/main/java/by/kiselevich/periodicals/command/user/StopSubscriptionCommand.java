package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class StopSubscriptionCommand extends AbstractSubscriptionCommand implements Command {
    private final SubscriptionService subscriptionService;

    public StopSubscriptionCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int subscriptionId = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            Optional<Subscription> optionalSubscription = subscriptionService.getSubscriptions(subscriptionId);
            if (!optionalSubscription.isPresent()) {
                String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), ResourceBundleMessages.SUBSCRIPTION_NOT_FOUND.getKey());
                writeMessageToResponse(resp, message);
                return Page.EMPTY_PAGE;
            }
            User user = getUserFromSession(req);
            subscriptionService.stopSubscription(optionalSubscription.get(), user);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
