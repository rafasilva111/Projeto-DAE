package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Pesagens extends SinaisBiomedicos implements Serializable {
    @Id
    private String id;
    private Float peso;
    private Float altura;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;

    public Pesagens(Date date, String id, Float peso, Float altura, UtilizadorNormal utilizadorNormal) {
        super(date);
        this.id = id;
        this.peso = peso;
        this.altura = altura;
        this.utilizadorNormal = utilizadorNormal;
    }

    public Pesagens() {
        super();
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

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }
}
