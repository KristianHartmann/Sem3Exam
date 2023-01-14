package dtos;

import security.entities.Role;
import security.entities.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link security.entities.User} entity
 */
public class UserDto implements Serializable {
    @NotNull
    private String userName;
    @NotNull
    private String userPass;
    @NotNull
    private String role;

    public UserDto(String userName, String role, String userPass) {
        this.userName = userName;
        this.role = role;
    }
    public UserDto(String userName) {
        this.userName = userName;
    }
    public UserDto(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return  Objects.equals(this.userName, entity.userName) &&
                Objects.equals(this.userPass, entity.userPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userPass);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userName = " + userName + ", " +
                "userPass = " + userPass + ")";
    }
}