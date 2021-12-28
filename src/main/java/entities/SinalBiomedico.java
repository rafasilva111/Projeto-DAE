package entities;

import entities.enums.Classification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@NamedQueries({
        @NamedQuery(
                name = "getAllBioSignals",
                query = "SELECT s FROM SinalBiomedico s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class SinalBiomedico implements Serializable {


    @Id
    private String id;
    private Date date;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;
    private Classification classification;
    private String descricao;

    public SinalBiomedico() {
    }

    public SinalBiomedico(long id,UtilizadorNormal utilizadorNormal,Classification classification,String descricao) {
        this.date= new Date(System.currentTimeMillis());
        this.id = id+"";
        this.utilizadorNormal = utilizadorNormal;
        this.classification = classification;
        this.descricao = descricao;
    }

    public SinalBiomedico(long id,UtilizadorNormal utilizadorNormal) {
        this.date= new Date(System.currentTimeMillis());
        this.id = id+"";
        this.utilizadorNormal = utilizadorNormal;
        this.classification = Classification.erro;
        this.descricao = "";
    }

    public Classification getClassification() {


        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UtilizadorNormal getUtilizadorNormal() {
        return utilizadorNormal;
    }

    public void setUtilizadorNormal(UtilizadorNormal utilizadorNormal) {
        this.utilizadorNormal = utilizadorNormal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SinalBiomedico{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", utilizadorNormal=" + utilizadorNormal +
                '}';
    }
}
