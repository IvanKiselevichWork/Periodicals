package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.entity.*;
import by.kiselevich.periodicals.exception.ValidatorException;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SubscriptionValidatorTest extends Assert {

    private static final SubscriptionValidator subscriptionValidator = SubscriptionValidator.getInstance();

    @Test
    public void testCheckSubscriptionPositive() throws ValidatorException {
        Subscription subscription = getSubscription();
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionNullSubscription() throws ValidatorException {
        subscriptionValidator.checkSubscription(null);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidEdition() throws ValidatorException {
        Subscription subscription = getSubscription();
        subscription.getEdition().setName("Invalid edition");
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidPeriod1() throws ValidatorException {
        Subscription subscription = getSubscription();
        subscription.setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2009-12-25T10:15:30")));
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidPeriod2() throws ValidatorException {
        Subscription subscription = getSubscription();
        subscription.setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2007-11-25T10:15:30")));
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidUser() throws ValidatorException {
        Subscription subscription = getSubscription();
        subscription.getUser().setLogin("invalid user login");
        subscriptionValidator.checkSubscription(subscription);
    }

    private Subscription getSubscription() {
        Edition edition = new Edition.EditionBuilder()
                .id(1)
                .name("Мир тяжелых моторов")
                .editionType(new EditionType.EditionTypeBuilder()
                        .id(1)
                        .type("Газета")
                        .build())
                .editionTheme(new EditionTheme.EditionThemeBuilder()
                        .id(1)
                        .title("Автомобили. Транспорт")
                        .build())
                .periodicityPerYear(24)
                .minimumSubscriptionPeriodInMonths(1)
                .priceForMinimumSubscriptionPeriod(BigDecimal.valueOf(5.8500))
                .build();
        User user = new User.UserBuilder()
                .id(2)
                .userRole(new UserRole.UserRoleBuilder()
                        .id(2)
                        .role("User")
                        .build())
                .login("user1")
                .password("d8b82fd86e72afd5d1fac1d417cb7e244e03aabcf6896f6338abbfe94378fde7ef9ea3f72f57b7d57e54cb98b3f78ed8588b8b3a78eb7fe2caf23fc556a84c04")
                .fullName("User")
                .email("user@periodicals.com")
                .money(BigDecimal.valueOf(4545809.8300))
                .build();
        return new Subscription.SubscriptionBuilder()
                .id(0)
                .edition(edition)
                .subscriptionStartDate(Timestamp.valueOf(LocalDateTime.parse("2007-12-25T10:15:30")))
                .subscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2008-12-25T10:15:30")))
                .user(user)
                .build();
    }
}
