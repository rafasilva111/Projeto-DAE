package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class BPM extends SinaisBiomedicos implements Serializable {
    @Id
    private String id;
    private int numeroBatimentos;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;

    public BPM(Date date, String id, int numeroBatimentos,UtilizadorNormal utilizadorNormal) {
        super(date);
        this.id = id;
        this.numeroBatimentos = numeroBatimentos;
        this.utilizadorNormal = utilizadorNormal;
    }


    public BPM() {

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

    public int getNumeroBatimentos() {
        return numeroBatimentos;
    }

    public void setNumeroBatimentos(int numeroBatimentos) {
        this.numeroBatimentos = numeroBatimentos;
    }
}
