package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class AddSubscriptionCommand extends AbstractSubscriptionCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddSubscriptionCommand.class);

    private final SubscriptionService subscriptionService;

    public AddSubscriptionCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Subscription subscription = buildSubscriptionFromRequest(req);
            subscriptionService.addSubscription(subscription);
        } catch (NumberFormatException e) {
            LOG.info(e);
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), ResourceBundleMessages.INVALID_DATA_FORMAT.getKey());
            writeMessageToResponse(resp, message);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
