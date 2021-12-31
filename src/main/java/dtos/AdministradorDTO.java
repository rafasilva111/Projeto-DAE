package dtos;

import entities.enums.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdministradorDTO implements Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private String data;
    private boolean superUser;


    public AdministradorDTO(String id, String password, String email, Date data, String userName, boolean superUser)  {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.data = data.toString();
        this.superUser = superUser;


    }

    public AdministradorDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }
}