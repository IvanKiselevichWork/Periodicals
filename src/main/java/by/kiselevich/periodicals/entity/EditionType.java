package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class EditionType implements Serializable {

    private static final long serialVersionUID = -4941478345701599265L;

    int id;
    String type;

    public EditionType() {

    }

    public EditionType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public static class EditionTypeBuilder {
        private int id;
        private String type;

        public EditionTypeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EditionTypeBuilder type(String type) {
            this.type = type;
            return this;
        }

        public EditionType build() {
            return new EditionType(id, type);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditionType that = (EditionType) o;

        if (id != that.id) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EditionType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
