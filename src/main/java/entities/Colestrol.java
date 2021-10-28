package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Colestrol extends SinaisBiomedicos implements Serializable {
    @Id
    private String id;
    private float nivelColestrol;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;

    public Colestrol(Date date, String id, float nivelColestrol, UtilizadorNormal utilizadorNormal) {
        super(date);
        this.id = id;
        this.nivelColestrol = nivelColestrol;
        this.utilizadorNormal = utilizadorNormal;
    }

    public Colestrol() {
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

    public float getNivelColestrol() {
        return nivelColestrol;
    }

    public void setNivelColestrol(float nivelColestrol) {
        this.nivelColestrol = nivelColestrol;
    }
}
