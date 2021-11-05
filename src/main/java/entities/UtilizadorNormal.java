package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NAME","ID"})})
@NamedQueries({
        @NamedQuery(
                name = "getAllNormUsers",
                query = "SELECT s FROM UtilizadorNormal s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class UtilizadorNormal extends Utilizador implements Serializable {

    private Date dataInicio;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<BPM> bpmList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Pesagem> pesagemList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Colestrol> colestrolList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Outros> outrosList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Prescricao> prescricoesList;

    public UtilizadorNormal( String name, String password, @Email String email) {
        super( name, password, email);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
        this.dataInicio = new Date(System.currentTimeMillis());
    }


    public UtilizadorNormal() {

    }

    public UtilizadorNormal(int id, String name, String password, String email) {
        super( id,name, password, email);
        this.bpmList = new LinkedList<>();
        this.pesagemList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
        this.dataInicio = new Date(System.currentTimeMillis());
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
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
    public void addOutrosRegister(Outros colestrol){
        this.outrosList.add(colestrol);
    }


    public List<Colestrol> getColestrolList() {
        return colestrolList;
    }

    public void setColestrolList(List<Colestrol> colestrolList) {
        this.colestrolList = colestrolList;
    }

    public List<Outros> getOutrosList() {
        return outrosList;
    }

    public void setOutrosList(List<Outros> outrosList) {
        this.outrosList = outrosList;
    }

    public List<Prescricao> getPrescricoesList() {
        return prescricoesList;
    }

    public void setPrescricoesList(List<Prescricao> prescricoesList) {
        this.prescricoesList = prescricoesList;
    }
}
