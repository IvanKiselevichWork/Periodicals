package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;

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

    public Map<Edition, Map.Entry<EditionTheme, EditionType>> getEditionAndThemeAndTypeMap(List<Edition> editions, List<EditionTheme> editionThemes, List<EditionType> types) {
        Map<Edition, Map.Entry<EditionTheme, EditionType>> map = new HashMap<>();
        Map.Entry<EditionTheme, EditionType> entry;
        EditionTheme foundEditionTheme = null;
        EditionType foundType = null;
        for (Edition edition : editions) {
            for (EditionTheme editionTheme : editionThemes) {
                if (edition.getThemeId() == editionTheme.getId()) {
                    foundEditionTheme = editionTheme;
                    break;
                }
            }
            for (EditionType editionType : types) {
                if (edition.getTypeId() == editionType.getId()) {
                    foundType = editionType;
                    break;
                }
            }
            entry = new EntityEntry<>(foundEditionTheme, foundType);
            map.put(edition, entry);
        }
        return map;
    }
}