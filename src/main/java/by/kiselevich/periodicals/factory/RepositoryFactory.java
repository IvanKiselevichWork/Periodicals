package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.repository.edition.EditionRepository;
import by.kiselevich.periodicals.repository.edition.EditionRepositoryImpl;
import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.repository.payment.PaymentRepositoryImpl;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepository;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepositoryImpl;
import by.kiselevich.periodicals.repository.theme.ThemeRepository;
import by.kiselevich.periodicals.repository.theme.ThemeRepositoryImpl;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.repository.user.UserRepositoryImpl;

public class RepositoryFactory {

    private UserRepository userRepository;
    private EditionRepository editionRepository;
    private PaymentRepository paymentRepository;
    private SubscriptionRepository subscriptionRepository;
    private ThemeRepository themeRepository;

    private RepositoryFactory(){
        userRepository = new UserRepositoryImpl();
        editionRepository = new EditionRepositoryImpl();
        paymentRepository = new PaymentRepositoryImpl();
        subscriptionRepository = new SubscriptionRepositoryImpl();
        themeRepository = new ThemeRepositoryImpl();
    }

    private static class RepositoryFactoryHolder {
        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    }

    public static RepositoryFactory getInstance() {
        return RepositoryFactory.RepositoryFactoryHolder.INSTANCE;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public EditionRepository getEditionRepository() {
        return editionRepository;
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public ThemeRepository getThemeRepository() {
        return themeRepository;
    }
}
