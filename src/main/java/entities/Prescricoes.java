package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Prescricoes implements Serializable {
    @Id
    private String id;
    private Date dataInicio;
    private Date dataFim;
    private typePrescricoes tipo;
    private String descricao;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;


    public Prescricoes(String id, Date dataFim, typePrescricoes tipo, String descricao, UtilizadorNormal utilizadorNormal) {
        this.id = id;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.descricao = descricao;
        this.utilizadorNormal = utilizadorNormal;
    }

    public Prescricoes() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public typePrescricoes getTipo() {
        return tipo;
    }

    public void setTipo(typePrescricoes tipo) {
        this.tipo = tipo;
    }

    private enum  typePrescricoes {
        exercicio,
        medica,
        nutricional
    }
}
