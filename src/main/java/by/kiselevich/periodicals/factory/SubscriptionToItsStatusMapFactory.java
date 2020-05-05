package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Subscription;

import java.sql.Timestamp;
import java.util.*;

/**
 * Class for building {@link Map} with {@link Subscription} as key and {@link String} contains {@link Subscription} status (active, expired) as value from {@code List<Subscription>}
 */
public class SubscriptionToItsStatusMapFactory {

    private SubscriptionToItsStatusMapFactory() {

    }

    public static Map<Subscription, String> getSubscriptionAndStatusMap(List<Subscription> subscriptionList) {
        Map<Subscription, String> subscriptionAndStatusMap = new TreeMap<>(Comparator.comparing(Subscription::getId));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        for (Subscription subscription : subscriptionList) {
            if (subscription.getSubscriptionEndDate().after(currentTimestamp)) {
                subscriptionAndStatusMap.put(subscription, ResourceBundleMessages.ACTIVE.getKey());
            } else {
                subscriptionAndStatusMap.put(subscription, ResourceBundleMessages.EXPIRED.getKey());
            }
        }
        return subscriptionAndStatusMap;
    }
}
