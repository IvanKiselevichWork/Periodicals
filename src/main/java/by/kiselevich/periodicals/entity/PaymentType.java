package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class PaymentType implements Serializable {

    private static final long serialVersionUID = -9128783422591446558L;

    private int id;
    private String type;

    public PaymentType() {

    }

    public PaymentType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public static class PaymentTypeBuilder {

        private int id;
        private String type;

        public PaymentTypeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public PaymentTypeBuilder type(String type) {
            this.type = type;
            return this;
        }

        public PaymentType build() {
            return new PaymentType(id, type);
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

        PaymentType that = (PaymentType) o;

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
        return "PaymentType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
