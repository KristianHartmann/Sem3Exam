package dtos;

import security.entities.User;

public class UserDto {
    private int id;
    private String username;

    public UserDto(User user) {
        this.id = user.getUserId();
        this.username = user.getUserName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
