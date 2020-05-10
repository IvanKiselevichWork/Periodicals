package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSubscriptionCommand {
    private static final Logger LOG = LogManager.getLogger(AbstractSubscriptionCommand.class);

    private static final String USER_NOT_FOUND = "USER NOT FOUND IN DATABASE WITH LOGIN FROM SESSION";
    private static final String EDITION_NOT_FOUND = "EDITION NOT FOUND OR BLOCKED IN DATABASE WITH ID FROM SUBSCRIPTION REQUEST";

    private final UserService userService;
    private final EditionService editionService;

    public AbstractSubscriptionCommand() {
        userService = ServiceFactory.getInstance().getUserService();
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    /**
     * Get data from {@link HttpServletRequest} and build {@link Subscription}
     * @param req {@link HttpServletRequest} with {@code JspParameter.PERIOD}, {@code JspParameter.ID}
     * and {@link javax.servlet.http.HttpSession} with {@code Attribute.LOGIN}
     * @return fully build {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     * @throws NumberFormatException if data format invalid
     */
    protected Subscription buildSubscriptionFromRequest(HttpServletRequest req) throws ServiceException {
        User user = getUserFromSession(req);
        Edition edition = getEditionFromRequest(req);
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

    protected User getUserFromSession(HttpServletRequest req) throws ServiceException {
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
