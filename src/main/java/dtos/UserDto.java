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
    private final String userName;
    @NotNull
    private final String userPass;
    @NotNull
    private final String role;

    public UserDto(User user, String role) {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
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