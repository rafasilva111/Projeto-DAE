package entities;

import entities.enums.Classification;

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
    private Float IMC;


    public Pesagem() {
        super();
    }

    public Pesagem(int id, float imc, float peso, Classification classification,String descricao, float altura, UtilizadorNormal utilizadorNormal) {
        super(id,utilizadorNormal,classification,descricao);

        this.peso = peso;
        this.altura = altura;
        this.IMC = imc;
    }

    public Float getIMC() {
        return IMC;
    }

    public void setIMC(Float IMC) {
        this.IMC = IMC;
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
