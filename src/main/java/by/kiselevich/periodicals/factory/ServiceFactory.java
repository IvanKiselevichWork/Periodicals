package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.edition.EditionServiceImpl;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeServiceImpl;
import by.kiselevich.periodicals.service.payment.PaymentService;
import by.kiselevich.periodicals.service.payment.PaymentServiceImpl;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import by.kiselevich.periodicals.service.subscription.SubscriptionServiceImpl;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeServiceImpl;
import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.service.user.UserServiceImpl;

public class ServiceFactory {

    private UserService userService;
    private EditionService editionService;
    private PaymentService paymentService;
    private SubscriptionService subscriptionService;
    private EditionThemeService editionThemeService;
    private EditionTypeService editionTypeService;

    private ServiceFactory(){
        userService = new UserServiceImpl();
        editionService = new EditionServiceImpl();
        paymentService = new PaymentServiceImpl();
        subscriptionService = new SubscriptionServiceImpl();
        editionThemeService = new EditionThemeServiceImpl();
        editionTypeService = new EditionTypeServiceImpl();
    }

    private static class ServiceFactoryHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return ServiceFactory.ServiceFactoryHolder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public EditionService getEditionService() {
        return editionService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }

    public EditionThemeService getEditionThemeService() {
        return editionThemeService;
    }

    public EditionTypeService getEditionTypeService() {
        return editionTypeService;
    }
}
