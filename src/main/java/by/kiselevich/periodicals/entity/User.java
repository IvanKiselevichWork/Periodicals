package by.kiselevich.periodicals.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = ("user"))
public class User implements Serializable {

    private static final long serialVersionUID = -3056882988523044542L;

    private static final BigDecimal DEFAULT_MONEY = BigDecimal.valueOf(0);
    private static final boolean DEFAULT_AVAILABILITY = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("id"))
    private int id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    @Column(name = ("login"))
    private String login;

    @Column(name = ("password"))
    private String password;

    @Column(name = ("full_name"))
    private String fullName;

    @Column(name = ("email"))
    private String email;

    @Column(name = ("money"))
    private BigDecimal money = DEFAULT_MONEY;

    @Column(name = ("is_available"), columnDefinition = "TINYINT")
    private boolean available = DEFAULT_AVAILABILITY;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Subscription> subscriptions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Payment> payments;

    public User() {

    }

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.userRole = userBuilder.userRole;
        this.login = userBuilder.login;
        this.password = userBuilder.password;
        this.fullName = userBuilder.fullName;
        this.email = userBuilder.email;
        this.money = userBuilder.money;
        this.available = userBuilder.available;
    }

    public static class UserBuilder {

        private int id;
        private UserRole userRole;
        private String login;
        private String password;
        private String fullName;
        private String email;
        private BigDecimal money = DEFAULT_MONEY;
        private boolean available = DEFAULT_AVAILABILITY;

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder userRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder money(BigDecimal money) {
            this.money = money;
            return this;
        }

        public UserBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(available, user.available)
                .append(userRole, user.userRole)
                .append(login, user.login)
                .append(password, user.password)
                .append(fullName, user.fullName)
                .append(email, user.email)
                .append(money.stripTrailingZeros(), user.money.stripTrailingZeros())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userRole)
                .append(login)
                .append(password)
                .append(fullName)
                .append(email)
                .append(money.stripTrailingZeros())
                .append(available)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userRole=" + userRole +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                ", available=" + available +
                '}';
    }
}
