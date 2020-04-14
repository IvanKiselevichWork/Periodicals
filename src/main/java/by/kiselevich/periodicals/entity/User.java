package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = -3056882988523044542L;

    private static final BigDecimal DEFAULT_MONEY = BigDecimal.valueOf(0);
    private static final boolean DEFAULT_AVAILABILITY = true;

    private int id;
    private UserRole userRole;
    private String login;
    private String password;
    private String fullName;
    private String email;
    private BigDecimal money = DEFAULT_MONEY;
    private boolean available = DEFAULT_AVAILABILITY;

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

    public UserRole getRole() {
        return userRole;
    }

    public void setRole(UserRole userRole) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!Objects.equals(userRole, user.userRole)) return false;
        if (available != user.available) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(fullName, user.fullName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleId=" + userRole +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                ", isAvailable=" + available +
                '}';
    }
}
