package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Subscription implements Serializable {

    private static final long serialVersionUID = -2727013561485656825L;

    private int id;
    private int editionId;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    private int userId;

    public Subscription() {

    }

    public Subscription(int id, int editionId, LocalDateTime subscriptionStartDate, LocalDateTime subscriptionEndDate, int userId) {
        this.id = id;
        this.editionId = editionId;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    public LocalDateTime getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDateTime getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != that.id) return false;
        if (editionId != that.editionId) return false;
        if (userId != that.userId) return false;
        if (subscriptionStartDate != null ? !subscriptionStartDate.equals(that.subscriptionStartDate) : that.subscriptionStartDate != null)
            return false;
        return subscriptionEndDate != null ? subscriptionEndDate.equals(that.subscriptionEndDate) : that.subscriptionEndDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + editionId;
        result = 31 * result + (subscriptionStartDate != null ? subscriptionStartDate.hashCode() : 0);
        result = 31 * result + (subscriptionEndDate != null ? subscriptionEndDate.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", editionId=" + editionId +
                ", subscriptionStartDate=" + subscriptionStartDate +
                ", subscriptionEndDate=" + subscriptionEndDate +
                ", userId=" + userId +
                '}';
    }
}
