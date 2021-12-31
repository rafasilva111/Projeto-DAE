package dtos;

import entities.Prescricao;
import entities.enums.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DoutorDTO implements Serializable {

    private String id;
    private String username;
    private String password;
    private String email;
    private String data;
    private String tipo;
    private List<PrescricaoDTO> prescricaoDTOList;
    private List<UtilizadorDTO> utilizadorDTOList;


    public DoutorDTO(String id, String password, String email, Date data, String userName, UserType tipo, List<PrescricaoDTO> prescricaoDTOList ,List<UtilizadorDTO> utilizadorDTOList )  {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.data = data.toString();
        this.tipo = tipo.toString();
        this.prescricaoDTOList = prescricaoDTOList;
        this.utilizadorDTOList = utilizadorDTOList;

    }

    public DoutorDTO() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<PrescricaoDTO> getPrescricaoDTOList() {
        return prescricaoDTOList;
    }

    public void setPrescricaoDTOList(List<PrescricaoDTO> prescricaoDTOList) {
        this.prescricaoDTOList = prescricaoDTOList;
    }

    public List<UtilizadorDTO> getUtilizadorDTOList() {
        return utilizadorDTOList;
    }

    public void setUtilizadorDTOList(List<UtilizadorDTO> utilizadorDTOList) {
        this.utilizadorDTOList = utilizadorDTOList;
    }
}