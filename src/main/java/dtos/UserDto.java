package dtos;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link security.entities.User} entity
 */
public class UserDto implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String userPass;
    @NotNull
    private String role;

    public UserDto(String userName, String role, String userPass) {
        this.username = userName;
        this.role = role;
    }
    public UserDto(String userName) {
        this.username = userName;
    }
    public UserDto(String userName, String role) {
        this.username = userName;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPass() {
        return userPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return  Objects.equals(this.username, entity.username) &&
                Objects.equals(this.userPass, entity.userPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userPass);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userName = " + username + ", " +
                "userPass = " + userPass + ")";
    }
}