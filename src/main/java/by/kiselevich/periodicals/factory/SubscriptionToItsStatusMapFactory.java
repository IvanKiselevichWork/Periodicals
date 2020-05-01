package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Subscription;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionToItsStatusMapFactory {

    private SubscriptionToItsStatusMapFactory() {

    }

    public static Map<Subscription, String> getSubscriptionAndStatusMap(List<Subscription> subscriptionList) {
        Map<Subscription, String> subscriptionAndStatusMap = new HashMap<>();
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
