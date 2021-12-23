package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "getAllPesagemRegisters",
                query = "SELECT s FROM Pesagem s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class Pesagem extends SinalBiomedico implements Serializable {

    private Float peso;
    private Float altura;


    public Pesagem() {
        super();
    }

    public Pesagem(int id, float peso, float altura, UtilizadorNormal utilizadorNormal) {
        super(id,utilizadorNormal);

        this.peso = peso;
        this.altura = altura;
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
