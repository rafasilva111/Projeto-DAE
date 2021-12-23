package dtos;

import entities.UserType;

import java.util.Date;

public class UtilizadorDTO {

    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String data;
    private String tipo;


    public UtilizadorDTO(String id, String password, String name, String email, Date data, String userName, UserType tipo) {
    this.id = id;
    this.name = name;
    this.username = userName;
    this.password = password;
    this.email = email;
    this.data = data.toString();
    if (tipo == UserType.UtilizadorNormal){
        this.tipo = "UtilizadorNormal";
    }else{
        this.tipo = "Administrador";
    }
    }

    public UtilizadorDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
