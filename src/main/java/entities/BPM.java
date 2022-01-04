package entities;

import entities.enums.Classification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NamedQueries({
        @NamedQuery(
                name = "getBpmRegisters",
                query = "SELECT s FROM BPM s WHERE S.deleted = FALSE ORDER BY s.id  " // JPQL
        ),
        @NamedQuery(
                name = "getAllBpmRegisters",
                query = "SELECT s FROM BPM s ORDER BY s.id  " // JPQL
        ),
})
@Entity
public class BPM extends SinalBiomedico implements Serializable {

    private int numeroBatimentos;


    public BPM(int numeroBatimentos, UtilizadorNormal utilizadorNormal, Classification classification,String descricao) {
        super(utilizadorNormal,classification,descricao);
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
