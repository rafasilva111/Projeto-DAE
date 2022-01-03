package entities;

import org.eclipse.persistence.internal.oxm.schema.model.All;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Table(uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
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
    @ManyToOne(cascade = ALL)
    private Doutor doctor;
    @OneToMany(cascade =ALL,mappedBy = "UtilizadorNormal")
    private List<Document> documents = new LinkedList<>();

    public UtilizadorNormal() {

    }

    public UtilizadorNormal( String password,@Email String email,String username,Doutor doutor) {
        super( password, email,username);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
        this.doctor = doutor;

    }
    public UtilizadorNormal( String password,@Email String email,String username) {
        super( password, email,username);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
        this.doctor = null;
    }



    public List<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document){
        this.documents.add(document);
    }


    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Doutor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doutor doctor) {
        this.doctor = doctor;
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
    public void remove(BPM colestrol) {

       if (this.getBpmList().contains(colestrol)){
           List<BPM> colestrols = this.getBpmList();
           colestrols.remove(colestrol);
           this.bpmList = colestrols;
       }

    }
    public void remove(Pesagem colestrol) {

       if (this.pesagemList.contains(colestrol)){
           List<Pesagem> colestrols = this.getPesagemList();
           colestrols.remove(colestrol);
           this.pesagemList = colestrols;
       }

    }
}
