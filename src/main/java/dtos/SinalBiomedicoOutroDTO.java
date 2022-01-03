package dtos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SinalBiomedicoOutroDTO implements Serializable {
    private String id;
    private String date;
    private String name;
    private String value;
    private String utilizadorNormalID;
    private String outroCategoriesID;
    private String descricao;

    public SinalBiomedicoOutroDTO(String id, Date date, String name, String value, String utilizadorNormalID, String outroCategoriesID, String descricao) {
        this.id = id;
        this.date = new SimpleDateFormat("dd/MM/yyyy kk:mm").format(date);
        this.name = name;
        this.value = value;
        this.utilizadorNormalID = utilizadorNormalID;
        this.outroCategoriesID = outroCategoriesID;
        this.descricao = descricao;
    }

    public SinalBiomedicoOutroDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUtilizadorNormalID() {
        return utilizadorNormalID;
    }

    public void setUtilizadorNormalID(String utilizadorNormalID) {
        this.utilizadorNormalID = utilizadorNormalID;
    }

    public String getOutroCategoriesID() {
        return outroCategoriesID;
    }

    public void setOutroCategoriesID(String outroCategoriesID) {
        this.outroCategoriesID = outroCategoriesID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
