package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMapsFactory {

    private EntityMapsFactory() {

    }

    private static class EntityMapsFactoryHolder {
        private static final EntityMapsFactory INSTANCE = new EntityMapsFactory();
    }

    public static EntityMapsFactory getInstance() {
        return EntityMapsFactoryHolder.INSTANCE;
    }

    public Map<Edition, Theme> getEditionAndThemeMap(List<Edition> editions, List<Theme> themes) {
        Map<Edition, Theme> editionThemeMap = new HashMap<>();
        for (Edition edition : editions) {
            for (Theme theme : themes) {
                if (edition.getThemeId() == theme.getId()) {
                    editionThemeMap.put(edition, theme);
                    break;
                }
            }
        }
        return editionThemeMap;
    }
}
