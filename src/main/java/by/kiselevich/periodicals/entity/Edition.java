package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Edition implements Serializable {

    private static final long serialVersionUID = -8443520786551186762L;

    private int id;
    private String name;
    private int typeId;
    private int themeId;
    private int periodicityPerYear;
    private int minimumSubscriptionPeriodInMonths;
    private BigDecimal priceForMinimumSubscriptionPeriod;

    public Edition() {

    }

    public Edition(int id, String name, int typeId, int themeId, int periodicityPerYear, int minimumSubscriptionPeriodInMonths, BigDecimal priceForMinimumSubscriptionPeriod) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.themeId = themeId;
        this.periodicityPerYear = periodicityPerYear;
        this.minimumSubscriptionPeriodInMonths = minimumSubscriptionPeriodInMonths;
        this.priceForMinimumSubscriptionPeriod = priceForMinimumSubscriptionPeriod;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edition edition = (Edition) o;

        if (id != edition.id) return false;
        if (typeId != edition.typeId) return false;
        if (themeId != edition.themeId) return false;
        if (periodicityPerYear != edition.periodicityPerYear) return false;
        if (minimumSubscriptionPeriodInMonths != edition.minimumSubscriptionPeriodInMonths) return false;
        if (!Objects.equals(name, edition.name)) return false;
        return Objects.equals(priceForMinimumSubscriptionPeriod, edition.priceForMinimumSubscriptionPeriod);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + themeId;
        result = 31 * result + periodicityPerYear;
        result = 31 * result + minimumSubscriptionPeriodInMonths;
        result = 31 * result + (priceForMinimumSubscriptionPeriod != null ? priceForMinimumSubscriptionPeriod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + typeId + '\'' +
                ", themeId=" + themeId +
                ", periodicityPerYear=" + periodicityPerYear +
                ", minimumSubscriptionPeriodInMonths=" + minimumSubscriptionPeriodInMonths +
                ", priceForMinimumSubscriptionPeriod=" + priceForMinimumSubscriptionPeriod +
                '}';
    }
}
