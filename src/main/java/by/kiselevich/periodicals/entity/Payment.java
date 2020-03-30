package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = -719674600971588113L;

    private int id;
    private int userId;
    private int typeId;
    private LocalDate date;
    private BigDecimal amount;
    private Integer subscriptionId;

    public Payment() {

    }

    public Payment(int id, int userId, int typeId, LocalDate date, BigDecimal amount, Integer subscriptionId) {
        this.id = id;
        this.userId = userId;
        this.typeId = typeId;
        this.date = date;
        this.amount = amount;
        this.subscriptionId = subscriptionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (id != payment.id) return false;
        if (userId != payment.userId) return false;
        if (typeId != payment.typeId) return false;
        if (!Objects.equals(date, payment.date)) return false;
        if (!Objects.equals(amount, payment.amount)) return false;
        return Objects.equals(subscriptionId, payment.subscriptionId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + typeId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (subscriptionId != null ? subscriptionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", typeId=" + typeId +
                ", date=" + date +
                ", amount=" + amount +
                ", subscriptionId=" + subscriptionId +
                '}';
    }
}
