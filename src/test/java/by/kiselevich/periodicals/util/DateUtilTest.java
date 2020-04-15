package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.entity.Subscription;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateUtilTest extends Assert {

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths1() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-12-25T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
        int expected = 0;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths2() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-06-26T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
        int expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths3() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-06-27T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
        int expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths4() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-06-26T10:15:30"));
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
        int expected = -6;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths5() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-12-25T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        Subscription subscription = getSubscription(start, end);
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
        int expected = 0;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths6() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-06-26T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        Subscription subscription = getSubscription(start, end);
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
        int expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths7() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-06-27T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        Subscription subscription = getSubscription(start, end);
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
        int expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void TestGetIntegerSubscriptionPeriodInMonths8() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.parse("2007-12-26T10:15:30"));
        Timestamp end = Timestamp.valueOf(LocalDateTime.parse("2007-06-26T10:15:30"));
        Subscription subscription = getSubscription(start, end);
        int result = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
        int expected = -6;
        assertEquals(expected, result);
    }

    private Subscription getSubscription(Timestamp start, Timestamp end) {
        return new Subscription.SubscriptionBuilder()
                .subscriptionStartDate(start)
                .subscriptionEndDate(end)
                .build();
    }
}
