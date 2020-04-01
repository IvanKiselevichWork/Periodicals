package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionType;
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

    static class EntityEntry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        public EntityEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }

    public Map<Edition, Map.Entry<Theme, EditionType>> getEditionAndThemeAndTypeMap(List<Edition> editions, List<Theme> themes, List<EditionType> types) {
        Map<Edition, Map.Entry<Theme, EditionType>> map = new HashMap<>();
        Map.Entry<Theme, EditionType> entry;
        Theme foundTheme = null;
        EditionType foundType = null;
        for (Edition edition : editions) {
            for (Theme theme : themes) {
                if (edition.getThemeId() == theme.getId()) {
                    foundTheme = theme;
                    break;
                }
            }
            for (EditionType editionType : types) {
                if (edition.getTypeId() == editionType.getId()) {
                    foundType = editionType;
                    break;
                }
            }
            entry = new EntityEntry<>(foundTheme, foundType);
            map.put(edition, entry);
        }
        return map;
    }
}