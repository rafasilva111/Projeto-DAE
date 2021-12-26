package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NamedQueries({
        @NamedQuery(
                name = "getAllBPMRegisters",
                query = "SELECT s FROM BPM s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class BPM extends SinalBiomedico implements Serializable {

    private int numeroBatimentos;


    public BPM(  int numeroBatimentos,UtilizadorNormal utilizadorNormal) {
        super();
        this.numeroBatimentos = numeroBatimentos;
       }

    public BPM(long id, int numeroBatimentos, UtilizadorNormal utilizadorNormal) {
        super(id,utilizadorNormal);
        this.numeroBatimentos = numeroBatimentos;

    }


    public BPM() {

    }


    public int getNumeroBatimentos() {
        return numeroBatimentos;
    }

    public void setNumeroBatimentos(int numeroBatimentos) {
        this.numeroBatimentos = numeroBatimentos;
    }
}
