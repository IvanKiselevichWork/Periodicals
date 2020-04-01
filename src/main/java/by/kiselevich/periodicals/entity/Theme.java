package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class Theme implements Serializable {

    private int id;
    private String title;

    public Theme() {

    }

    public Theme(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static class ThemeBuilder {
        private int id;
        private String title;

        public ThemeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ThemeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Theme build() {
            return new Theme(id, title);
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

        Theme theme = (Theme) o;

        if (id != theme.id) return false;
        return Objects.equals(title, theme.title);
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
