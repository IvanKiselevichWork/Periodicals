package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.payment.PaymentService;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import by.kiselevich.periodicals.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowAdminPageCommand implements Command {

    private final UserService userService;
    private final EditionService editionService;
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;

    public ShowAdminPageCommand() {
        userService = ServiceFactory.getInstance().getUserService();
        editionService = ServiceFactory.getInstance().getEditionService();
        paymentService = ServiceFactory.getInstance().getPaymentService();
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), DashboardPageOptionCommand.MAIN);

        try {
            int usersCount = userService.getAllUsers().size();
            int editionsCount = editionService.getAllEditions().size();
            int paymentsCount = paymentService.getAllPayments().size();
            int subscriptionsCount = subscriptionService.getAllSubscriptions().size();
            req.setAttribute(Attribute.USERS_COUNT.getValue(), usersCount);
            req.setAttribute(Attribute.EDITIONS_COUNT.getValue(), editionsCount);
            req.setAttribute(Attribute.PAYMENTS_COUNT.getValue(), paymentsCount);
            req.setAttribute(Attribute.SUBSCRIPTIONS_COUNT.getValue(), subscriptionsCount);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
