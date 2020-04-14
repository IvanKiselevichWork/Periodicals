package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class EditionTheme implements Serializable {

    private static final long serialVersionUID = 5438421720708516488L;

    private int id;
    private String title;

    public EditionTheme() {

    }

    private EditionTheme(EditionThemeBuilder editionThemeBuilder) {
        this.id = editionThemeBuilder.id;
        this.title = editionThemeBuilder.title;
    }

    public static class EditionThemeBuilder {
        private int id;
        private String title;

        public EditionThemeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EditionThemeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public EditionTheme build() {
            return new EditionTheme(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditionTheme editionTheme = (EditionTheme) o;

        if (id != editionTheme.id) return false;
        return Objects.equals(title, editionTheme.title);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
