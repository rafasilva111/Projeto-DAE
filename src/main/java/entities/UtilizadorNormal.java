package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NAME","ID"})})
@NamedQueries({
        @NamedQuery(
                name = "getAllNormUsers",
                query = "SELECT s FROM UtilizadorNormal s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class UtilizadorNormal extends Utilizador implements Serializable {


    @OneToMany(cascade=ALL,mappedBy = "UtilizadorNormal")
    private List<BPM> bpmList;
    @OneToMany(cascade=ALL,mappedBy = "UtilizadorNormal")
    private List<Pesagem> pesagemList;
    @OneToMany(cascade=ALL,mappedBy = "UtilizadorNormal")
    private List<Colestrol> colestrolList;
    @OneToMany(cascade=ALL,mappedBy = "UtilizadorNormal")
    private List<Outro> outrosList;
    @OneToMany(cascade=ALL,mappedBy = "UtilizadorNormal")
    private List<Prescricao> prescricoesList;

    public UtilizadorNormal( String name, String password, @Email String email,String username) {
        super( name, password, email,username);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
    }


    public UtilizadorNormal() {

    }

    public UtilizadorNormal(int id, String name, String password, String email,String username) {
        super( name, password, email,username);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();

    }


    public List<BPM> getBpmList() {
        return bpmList;
    }

    public void setBpmList(List<BPM> bpmList) {
        this.bpmList = bpmList;
    }

    public List<Pesagem> getPesagemList() {
        return pesagemList;
    }

    public void setPesagemList(List<Pesagem> pesagemList) {
        this.pesagemList = pesagemList;
    }

    public void addColestrolRegister(Colestrol colestrol){
        this.colestrolList.add(colestrol);
    }
    public void addPesagemRegister(Pesagem colestrol){
        this.pesagemList.add(colestrol);
    }
    public void addBpmRegister(BPM colestrol){
        this.bpmList.add(colestrol);
    }
    public void addPrescicaoRegister(Prescricao colestrol){
        this.prescricoesList.add(colestrol);
    }
    public void addOutrosRegister(Outro colestrol){
        this.outrosList.add(colestrol);
    }


    public List<Colestrol> getColestrolList() {
        return colestrolList;
    }

    public void setColestrolList(List<Colestrol> colestrolList) {
        this.colestrolList = colestrolList;
    }

    public List<Outro> getOutrosList() {
        return outrosList;
    }

    public void setOutrosList(List<Outro> outrosList) {
        this.outrosList = outrosList;
    }

    public List<Prescricao> getPrescricoesList() {
        return prescricoesList;
    }

    public void setPrescricoesList(List<Prescricao> prescricoesList) {
        this.prescricoesList = prescricoesList;
    }

    public void remove(Colestrol colestrol) {

       if (this.colestrolList.contains(colestrol)){
           List<Colestrol> colestrols = this.getColestrolList();
           colestrols.remove(colestrol);
           this.colestrolList = colestrols;
       }

    }
}
