package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import by.kiselevich.periodicals.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class AddSubscriptionCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddSubscriptionCommand.class);

    private static final String USER_NOT_FOUND = "USER NOT FOUND IN DATABASE WITH LOGIN FROM SESSION";
    private static final String EDITION_NOT_FOUND = "EDITION NOT FOUND OR BLOCKED IN DATABASE WITH ID FROM SUBSCRIPTION REQUEST";

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final EditionService editionService;

    public AddSubscriptionCommand() {
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
        userService = ServiceFactory.getInstance().getUserService();
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = getUserFromRequest(req);
            Edition edition = getEditionFromRequest(req);
            Subscription subscription = getSubscriptionFromRequest(req, user, edition);
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

    private Subscription getSubscriptionFromRequest(HttpServletRequest req, User user, Edition edition) {
        int subscriptionPeriod = Integer.parseInt(req.getParameter(JspParameter.PERIOD.getValue()));
        Timestamp start = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH, subscriptionPeriod);
        Timestamp end = new Timestamp(calendar.getTimeInMillis());
        return new Subscription.SubscriptionBuilder()
                .edition(edition)
                .subscriptionStartDate(start)
                .subscriptionEndDate(end)
                .user(user)
                .build();
    }

    private Edition getEditionFromRequest(HttpServletRequest req) throws ServiceException {
        int editionId = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
        List<Edition> editionList = editionService.getEditionsById(editionId, true);
        Edition edition;
        if (!editionList.isEmpty()) {
            edition = editionList.get(0);
        } else {
            LOG.warn(EDITION_NOT_FOUND);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
        return edition;
    }

    private User getUserFromRequest(HttpServletRequest req) throws ServiceException {
        String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
        Optional<User> optionalUser = userService.getUserByLogin(login);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            LOG.warn(USER_NOT_FOUND);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
        return user;
    }
}
