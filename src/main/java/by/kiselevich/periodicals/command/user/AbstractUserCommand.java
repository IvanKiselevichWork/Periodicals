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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractUserCommand {
    private static final Logger LOG = LogManager.getLogger(AbstractUserCommand.class);

    private static final String USER_NOT_FOUND = "USER NOT FOUND IN DATABASE WITH LOGIN FROM SESSION";
    private static final String EDITION_NOT_FOUND = "EDITION NOT FOUND OR BLOCKED IN DATABASE WITH ID FROM SUBSCRIPTION REQUEST";

    private final UserService userService;
    private final EditionService editionService;

    public AbstractUserCommand() {
        userService = ServiceFactory.getInstance().getUserService();
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    /**
     * Get data from {@link HttpServletRequest} and build {@link Subscription}
     *
     * @param req {@link HttpServletRequest} with {@code JspParameter.PERIOD}, {@code JspParameter.ID}
     * and {@link javax.servlet.http.HttpSession} with {@code Attribute.LOGIN}
     * @return fully build {@link Subscription}
     * @throws ServiceException      with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
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
        Edition edition = editionService.getEditionById(editionId, true);
        if (edition != null) {
            return edition;
        } else {
            LOG.warn(EDITION_NOT_FOUND);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    protected User getUserFromSession(HttpServletRequest req) throws ServiceException {
        String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
        User user = userService.getUserByLogin(login);
        if (user != null) {
            return user;
        } else {
            LOG.warn(USER_NOT_FOUND);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    protected List<Subscription> getSubscriptionsByUserLoginFromRequest(HttpServletRequest req) throws ServiceException {
        String userLogin = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
        List<Subscription> subscriptionList = new ArrayList<>();
        User user = userService.getUserByLogin(userLogin);
        if (user != null) {
            subscriptionList = user.getSubscriptions();
        }
        return subscriptionList;
    }
}
