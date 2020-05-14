package by.kiselevich.periodicals.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = ("user_role"))
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8285019634206760939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("id"))
    private int id;

    @Column(name = ("role"))
    private String role;

    public UserRole() {

    }

    private UserRole(UserRoleBuilder userRoleBuilder) {
        this.id = userRoleBuilder.id;
        this.role = userRoleBuilder.role;
    }

    public static class UserRoleBuilder {

        private int id;
        private String role;

        public UserRoleBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserRoleBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserRole build() {
            return new UserRole(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole that = (UserRole) o;

        if (id != that.id) return false;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
