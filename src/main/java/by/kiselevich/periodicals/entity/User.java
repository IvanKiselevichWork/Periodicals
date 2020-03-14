package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = -3056882988523044542L;

    private int id;
    private int roleId;
    private String login;
    private char[] password;
    private String fullName;
    private String email;
    private BigDecimal money;
    private boolean isAvailable;

    public User() {

    }

    public User(int id, int roleId, String login, char[] password, String fullName, String email, BigDecimal money, boolean isAvailable) {
        this.id = id;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.money = money;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
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
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (roleId != user.roleId) return false;
        if (isAvailable != user.isAvailable) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Arrays.equals(password, user.password)) return false;
        if (!Objects.equals(fullName, user.fullName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + roleId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
