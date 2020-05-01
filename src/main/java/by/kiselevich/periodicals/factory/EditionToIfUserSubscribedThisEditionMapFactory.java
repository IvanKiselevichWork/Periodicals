package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for building {@link Map} with {@link Edition} as key and {@link Boolean} contains if {@link List} with {@link Subscription} contains this {@link Edition} as value from {@code List<Edition>} and {@code List<Subscription>}
 */
public class EditionToIfUserSubscribedThisEditionMapFactory {

    private EditionToIfUserSubscribedThisEditionMapFactory() {

    }

    public static Map<Edition, Boolean> getEditionAndIfUserSubscribedMap(List<Edition> editionList, List<Subscription> subscriptionList) {
        Map<Edition, Boolean> editionAndIfUserSubscribedMap = new HashMap<>();
        boolean isSubscribed;
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        for (Edition edition : editionList) {
            isSubscribed = false;
            for (Subscription subscription : subscriptionList) {
                if (subscription.getEdition().equals(edition) && (subscription.getSubscriptionEndDate().after(currentTimestamp))) {
                    isSubscribed = true;
                    break;
                }
            }
            editionAndIfUserSubscribedMap.put(edition, isSubscribed);
        }
        return editionAndIfUserSubscribedMap;
    }
}
