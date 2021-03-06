package dtos;

import entities.enums.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UtilizadorDTO implements Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private String data;
    private String tipo;
    private List<SinalBiomedicoDTO> mysignals;
    private String doutorUsername;
    private String doutorId;

    public UtilizadorDTO(String id, String password, String email, Date data, String userName, UserType tipo) {
    this.id = id;
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
    public UtilizadorDTO(String id, String password, String email, Date data, String userName, UserType tipo, List<SinalBiomedicoDTO> signals, List<DocumentDTO> documents) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.data = data.toString();
        if (tipo == UserType.UtilizadorNormal){
            this.tipo = "UtilizadorNormal";
        }else{
            this.tipo = "Administrador";
        }
        this.mysignals = signals;
    }


    public UtilizadorDTO(String id, String password, String email, Date data, String userName, UserType utilizadorNormal, String doc, String doutorId) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.data = data.toString();
        if (utilizadorNormal == UserType.UtilizadorNormal){
            this.tipo = "UtilizadorNormal";
        }else{
            this.tipo = "Administrador";
        }
        this.doutorUsername = doc;
        this.doutorId = doutorId;
    }

    public List<SinalBiomedicoDTO> getMysignals() {
        return mysignals;
    }

    public void setMysignals(List<SinalBiomedicoDTO> mysignals) {
        this.mysignals = mysignals;
    }


    public UtilizadorDTO() {
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

    public String getDoutorId() {return doutorId;  }

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

    public String getDoutorUsername() {
        return doutorUsername;
    }

    public void setDoutorUsername(String doutorUsername) {
        this.doutorUsername = doutorUsername;
    }

    public void setDoutorId(String doutorId) {
        this.doutorId = doutorId;
    }
}
