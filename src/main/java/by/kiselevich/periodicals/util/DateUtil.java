package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.entity.Subscription;

import java.sql.Timestamp;
import java.time.Period;

public class DateUtil {

    private static final int MONTH_PER_YEAR = 12;
    private static final double MONTH_PER_DAY = 0.033;

    private DateUtil() {

    }

    public static int getIntegerSubscriptionPeriodInMonths(Subscription subscription) {
        Timestamp start = subscription.getSubscriptionStartDate();
        Timestamp end = subscription.getSubscriptionEndDate();
        return getPeriod(start, end);
    }

    public static int getIntegerSubscriptionPeriodInMonths(Timestamp start, Timestamp end) {
        return getPeriod(start, end);
    }

    private static int getPeriod(Timestamp start, Timestamp end) {
        Period period = Period.between(start.toLocalDateTime().toLocalDate(), end.toLocalDateTime().toLocalDate());
        return  (int) (period.getYears() * MONTH_PER_YEAR + period.getMonths() + period.getDays() * MONTH_PER_DAY);
    }
}
