package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "getAllColestrolRegisters",
                query = "SELECT s FROM Colestrol s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class Colestrol extends SinalBiomedico implements Serializable {

    private float nivelColestrol;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;

    public Colestrol( float nivelColestrol, UtilizadorNormal utilizadorNormal) {
        super();
        this.nivelColestrol = nivelColestrol;
        this.utilizadorNormal = utilizadorNormal;
    }

    public Colestrol(long id, float nivelColestrol, UtilizadorNormal utilizadorNormal) {
        super(id);
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


    public float getNivelColestrol() {
        return nivelColestrol;
    }

    public void setNivelColestrol(float nivelColestrol) {
        this.nivelColestrol = nivelColestrol;
    }


}
