package by.kiselevich.periodicals.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = ("edition"))
public class Edition implements Serializable {

    private static final long serialVersionUID = -8443520786551186762L;

    private static final boolean DEFAULT_BLOCKAGE = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("id"))
    private int id;

    @Column(name = ("name"))
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EditionType editionType;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private EditionTheme editionTheme;

    @Column(name = ("periodicity_per_year"))
    private int periodicityPerYear;

    @Column(name = ("minimum_subscription_period_in_months"))
    private int minimumSubscriptionPeriodInMonths;

    @Column(name = ("price_for_minimum_subscription_period"))
    private BigDecimal priceForMinimumSubscriptionPeriod;

    @Column(name = ("is_blocked"), columnDefinition = "TINYINT")
    private boolean blocked = DEFAULT_BLOCKAGE;

    public Edition() {

    }

    private Edition(EditionBuilder editionBuilder) {
        this.id = editionBuilder.id;
        this.name = editionBuilder.name;
        this.editionType = editionBuilder.editionType;
        this.editionTheme = editionBuilder.editionTheme;
        this.periodicityPerYear = editionBuilder.periodicityPerYear;
        this.minimumSubscriptionPeriodInMonths = editionBuilder.minimumSubscriptionPeriodInMonths;
        this.priceForMinimumSubscriptionPeriod = editionBuilder.priceForMinimumSubscriptionPeriod;
        this.blocked = editionBuilder.blocked;
    }

    public static class EditionBuilder {
        private int id;
        private String name;
        private EditionType editionType;
        private EditionTheme editionTheme;
        private int periodicityPerYear;
        private int minimumSubscriptionPeriodInMonths;
        private BigDecimal priceForMinimumSubscriptionPeriod;
        private boolean blocked = DEFAULT_BLOCKAGE;

        public EditionBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EditionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EditionBuilder editionType(EditionType editionType) {
            this.editionType = editionType;
            return this;
        }

        public EditionBuilder editionTheme(EditionTheme editionTheme) {
            this.editionTheme = editionTheme;
            return this;
        }

        public EditionBuilder periodicityPerYear(int periodicityPerYear) {
            this.periodicityPerYear = periodicityPerYear;
            return this;
        }

        public EditionBuilder minimumSubscriptionPeriodInMonths(int minimumSubscriptionPeriodInMonths) {
            this.minimumSubscriptionPeriodInMonths = minimumSubscriptionPeriodInMonths;
            return this;
        }

        public EditionBuilder priceForMinimumSubscriptionPeriod(BigDecimal priceForMinimumSubscriptionPeriod) {
            this.priceForMinimumSubscriptionPeriod = priceForMinimumSubscriptionPeriod;
            return this;
        }

        public EditionBuilder blocked(boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Edition build() {
            return new Edition(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public EditionTheme getEditionTheme() {
        return editionTheme;
    }

    public void setEditionTheme(EditionTheme editionTheme) {
        this.editionTheme = editionTheme;
    }

    public int getPeriodicityPerYear() {
        return periodicityPerYear;
    }

    public void setPeriodicityPerYear(int periodicityPerYear) {
        this.periodicityPerYear = periodicityPerYear;
    }

    public int getMinimumSubscriptionPeriodInMonths() {
        return minimumSubscriptionPeriodInMonths;
    }

    public void setMinimumSubscriptionPeriodInMonths(int minimumSubscriptionPeriodInMonths) {
        this.minimumSubscriptionPeriodInMonths = minimumSubscriptionPeriodInMonths;
    }

    public BigDecimal getPriceForMinimumSubscriptionPeriod() {
        return priceForMinimumSubscriptionPeriod;
    }

    public void setPriceForMinimumSubscriptionPeriod(BigDecimal priceForMinimumSubscriptionPeriod) {
        this.priceForMinimumSubscriptionPeriod = priceForMinimumSubscriptionPeriod;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return id == edition.id &&
                periodicityPerYear == edition.periodicityPerYear &&
                minimumSubscriptionPeriodInMonths == edition.minimumSubscriptionPeriodInMonths &&
                blocked == edition.blocked &&
                Objects.equals(name, edition.name) &&
                Objects.equals(editionType, edition.editionType) &&
                Objects.equals(editionTheme, edition.editionTheme) &&
                Objects.equals(priceForMinimumSubscriptionPeriod.stripTrailingZeros(), edition.priceForMinimumSubscriptionPeriod.stripTrailingZeros());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (editionType != null ? editionType.hashCode() : 0);
        result = 31 * result + (editionTheme != null ? editionTheme.hashCode() : 0);
        result = 31 * result + periodicityPerYear;
        result = 31 * result + minimumSubscriptionPeriodInMonths;
        result = 31 * result + (priceForMinimumSubscriptionPeriod != null ? priceForMinimumSubscriptionPeriod.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", editionType=" + editionType +
                ", editionTheme=" + editionTheme +
                ", periodicityPerYear=" + periodicityPerYear +
                ", minimumSubscriptionPeriodInMonths=" + minimumSubscriptionPeriodInMonths +
                ", priceForMinimumSubscriptionPeriod=" + priceForMinimumSubscriptionPeriod +
                ", isBlocked=" + blocked +
                '}';
    }
}
