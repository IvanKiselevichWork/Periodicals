package by.kiselevich.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class User implements Serializable {

    private static final long serialVersionUID = -3056882988523044542L;

    private int id;
    private String login;
    private String username;
    private int roleId;
    private char[] password;
    private BigDecimal money;
    private boolean isAvailable;

    public User() {

    }

    public User(int id, String login, String username, int roleId, char[] password, BigDecimal money, boolean isAvailable) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.roleId = roleId;
        this.password = password;
        this.money = money;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
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
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (!Arrays.equals(password, user.password)) return false;
        return money != null ? money.equals(user.money) : user.money == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", username='" + username + '\'' +
                ", roleId=" + roleId +
                ", password=" + Arrays.toString(password) +
                ", money=" + money +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
