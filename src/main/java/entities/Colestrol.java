package entities;

import entities.enums.Classification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "getColestrolRegisters",
                query = "SELECT s FROM Colestrol s WHERE S.deleted = FALSE ORDER BY s.id  " // JPQL
        ),
        @NamedQuery(
                name = "getAllColestrolRegisters",
                query = "SELECT s FROM Colestrol s ORDER BY s.id  " // JPQL
        ),
})
@Entity
public class Colestrol extends SinalBiomedico implements Serializable {

    private float nivelColestrol;


    public Colestrol( float nivelColestrol) {
        super();
        this.nivelColestrol = nivelColestrol;
    }

    public Colestrol(float nivelColestrol, UtilizadorNormal utilizadorNormal, Classification classification,String descricao) {
        super(utilizadorNormal,classification,descricao);
        this.nivelColestrol = nivelColestrol;

    }

    public Colestrol() {
        super();
    }


    public float getNivelColestrol() {
        return nivelColestrol;
    }

    public void setNivelColestrol(float nivelColestrol) {
        this.nivelColestrol = nivelColestrol;
    }

    @Override
    public String toString() {
        return "Colestrol{" +
                "nivelColestrol=" + nivelColestrol + ","+
                super.toString()+
                '}';
    }
}
