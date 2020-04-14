package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Subscription implements Serializable {

    private static final long serialVersionUID = -2727013561485656825L;

    private int id;
    private Edition edition;
    private Timestamp subscriptionStartDate;
    private Timestamp subscriptionEndDate;
    private User user;

    public Subscription() {

    }

    private Subscription(SubscriptionBuilder subscriptionBuilder) {
        this.id = subscriptionBuilder.id;
        this.edition = subscriptionBuilder.edition;
        this.subscriptionStartDate = subscriptionBuilder.subscriptionStartDate;
        this.subscriptionEndDate = subscriptionBuilder.subscriptionEndDate;
        this.user = subscriptionBuilder.user;
    }

    public static class SubscriptionBuilder {
        private int id;
        private Edition edition;
        private Timestamp subscriptionStartDate;
        private Timestamp subscriptionEndDate;
        private User user;

        public SubscriptionBuilder id(int id) {
            this.id = id;
            return this;
        }

        public SubscriptionBuilder edition(Edition edition) {
            this.edition = edition;
            return this;
        }

        public SubscriptionBuilder subscriptionStartDate(Timestamp subscriptionStartDate) {
            this.subscriptionStartDate = subscriptionStartDate;
            return this;
        }

        public SubscriptionBuilder subscriptionEndDate(Timestamp subscriptionEndDate) {
            this.subscriptionEndDate = subscriptionEndDate;
            return this;
        }

        public SubscriptionBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Timestamp getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Timestamp subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Timestamp getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Timestamp subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != that.id) return false;
        if (!Objects.equals(edition, that.edition)) return false;
        if (!Objects.equals(subscriptionStartDate, that.subscriptionStartDate))
            return false;
        if (!Objects.equals(subscriptionEndDate, that.subscriptionEndDate))
            return false;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (subscriptionStartDate != null ? subscriptionStartDate.hashCode() : 0);
        result = 31 * result + (subscriptionEndDate != null ? subscriptionEndDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", edition=" + edition +
                ", subscriptionStartDate=" + subscriptionStartDate +
                ", subscriptionEndDate=" + subscriptionEndDate +
                ", user=" + user +
                '}';
    }
}
