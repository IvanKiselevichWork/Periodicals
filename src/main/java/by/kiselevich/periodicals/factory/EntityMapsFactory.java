package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMapsFactory {

    private EntityMapsFactory() {

    }

    public static Map<Edition, Boolean> getEditionAndIfUserSubscribedMap(List<Edition> editionList, List<Subscription> subscriptionList) {
        Map<Edition, Boolean> editionAndIfUserSubscribedMap = new HashMap<>();
        boolean isSubscribed;
        for (Edition edition : editionList) {
            isSubscribed = false;
            for (Subscription subscription : subscriptionList) {
                if (subscription.getEdition().equals(edition) && (subscription.getSubscriptionEndDate().after(new Timestamp(System.currentTimeMillis())))) {
                    isSubscribed = true;
                    break;
                }

            }
            editionAndIfUserSubscribedMap.put(edition, isSubscribed);
        }
        return editionAndIfUserSubscribedMap;
    }
}
