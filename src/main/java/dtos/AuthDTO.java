package dtos;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    private String password;
    private String username;

    public AuthDTO(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public AuthDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
