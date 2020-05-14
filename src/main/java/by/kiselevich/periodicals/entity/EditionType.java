package by.kiselevich.periodicals.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = ("edition_type"))
public class EditionType implements Serializable {

    private static final long serialVersionUID = -4941478345701599265L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("id"))
    int id;

    @Column(name = ("type"))
    String type;

    public EditionType() {

    }

    private EditionType(EditionTypeBuilder editionTypeBuilder) {
        this.id = editionTypeBuilder.id;
        this.type = editionTypeBuilder.type;
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
            return new EditionType(this);
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
