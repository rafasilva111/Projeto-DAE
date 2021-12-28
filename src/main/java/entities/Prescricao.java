package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Prescricao implements Serializable {
    @Id
    private String id;
    private Date dataInicio;
    private Date dataFim;
    private TypePrescricoes tipo;
    private String descricao;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;
    @ManyToOne
    private Doutor doutor;


    public Prescricao(Date dataFim, TypePrescricoes tipo, String descricao, UtilizadorNormal utilizadorNormal,Doutor doutor) {
        this.id = System.currentTimeMillis()+"";
        this.dataInicio = new Date(System.currentTimeMillis());
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.descricao = descricao;
        this.utilizadorNormal = utilizadorNormal;
        this.doutor = doutor;
    }

    public Prescricao() {
    }

    public Doutor getDoutor() {
        return doutor;
    }

    public void setDoutor(Doutor doutor) {
        this.doutor = doutor;
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

    public TypePrescricoes getTipo() {
        return tipo;
    }

    public void setTipo(TypePrescricoes tipo) {
        this.tipo = tipo;
    }

    public enum  TypePrescricoes {
        exercicio,
        medica,
        nutricional
    }
}
