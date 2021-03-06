package dtos;

import entities.UtilizadorNormal;
import entities.enums.Classification;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SinalBiomedicoDTO implements Serializable {

    private String id;
    private String date;
    private String name;
    private List<String> value;
    private String utilizadorNormalID;
    private String descricao;
    private String classification;


    public SinalBiomedicoDTO(String id, Date date, String name, List<String> value,  String utilizadorNormalID, String descricao, Classification classification) {
        this.id = id;
        this.name = name;
        this.date = new SimpleDateFormat("dd/MM/yyyy kk:mm").format(date);
        this.value = value;
        this.utilizadorNormalID = utilizadorNormalID;
        this.descricao = descricao;

        switch (classification){
            case muitoalto:
                this.classification="Muito Alto";
                break;
            case alto:
                this.classification="Alto";
                break;
            case medio:
                this.classification="Medio";
                break;
            case baixo:
                this.classification="Baixo";
                break;
            case muitobaixo:
                this.classification="Muito Baixo";
                break;
        }

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public SinalBiomedicoDTO() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void addValue(String value){
        this.value.add(value);
    }
    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }


    public String getUtilizadorNormalID() {
        return utilizadorNormalID;
    }

    public void setUtilizadorNormalID(String utilizadorNormalID) {
        this.utilizadorNormalID = utilizadorNormalID;
    }

    @Override
    public String toString() {
        return "SinalBiomedicoDTO{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", utilizadorNormalID='" + utilizadorNormalID + '\'' +
                ", descricao='" + descricao + '\'' +
                ", classification='" + classification + '\'' +
                '}';
    }
}
