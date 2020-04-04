package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = -719674600971588113L;

    private int id;
    private User user;
    private PaymentType paymentType;
    private Timestamp date;
    private BigDecimal amount;
    private Subscription subscription;

    public Payment() {

    }

    public Payment(int id, User user, PaymentType paymentType, Timestamp date, BigDecimal amount, Subscription subscription) {
        this.id = id;
        this.user = user;
        this.paymentType = paymentType;
        this.date = date;
        this.amount = amount;
        this.subscription = subscription;
    }

    public static class PaymentBuilder {
        private int id;
        private User user;
        private PaymentType paymentType;
        private Timestamp date;
        private BigDecimal amount;
        private Subscription subscription;

        public PaymentBuilder id(int id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PaymentBuilder paymentType(PaymentType paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public PaymentBuilder date(Timestamp date) {
            this.date = date;
            return this;
        }

        public PaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder subscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public Payment build() {
            return new Payment(id, user, paymentType, date, amount, subscription);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (id != payment.id) return false;
        if (!Objects.equals(user, payment.user)) return false;
        if (!Objects.equals(paymentType, payment.paymentType)) return false;
        if (!Objects.equals(date, payment.date)) return false;
        if (!Objects.equals(amount, payment.amount)) return false;
        return Objects.equals(subscription, payment.subscription);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (paymentType != null ? paymentType.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", user=" + user +
                ", paymentType=" + paymentType +
                ", date=" + date +
                ", amount=" + amount +
                ", subscription=" + subscription +
                '}';
    }
}
