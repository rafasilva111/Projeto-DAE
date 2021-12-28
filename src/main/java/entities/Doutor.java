package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
@NamedQueries({
        @NamedQuery(
                name = "getAllDoctors",
                query = "SELECT s FROM Doutor s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class Doutor extends Utilizador implements Serializable {

    @OneToMany(cascade=ALL,mappedBy = "Doctor")
    private List<UtilizadorNormal> patients;
    @OneToMany(cascade=ALL,mappedBy = "Doutor")
    private List<Prescricao> prescricoes;

    public Doutor(String password, @Email String email, String userName) {
        super(password, email, userName);
        this.patients = new LinkedList<>();
        this.prescricoes = new LinkedList<>();
    }


    public Doutor() {

    }


    public List<UtilizadorNormal> getPatients() {
        return patients;
    }

    public void setPatient(List<UtilizadorNormal> patients) {
        this.patients = patients;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }

    public void addPrescicaoRegister(Prescricao colestrol){
        this.prescricoes.add(colestrol);
    }
}