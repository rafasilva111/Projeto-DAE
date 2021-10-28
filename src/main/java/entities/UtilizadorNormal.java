package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class UtilizadorNormal extends Utilizador implements Serializable {

    private Date dataInicio;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<BPM> bpmList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Pesagens> pesagensList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Colestrol> colestrolList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Outros> outrosList;
    @OneToMany(mappedBy = "utilizadorNormal")
    private List<Prescricoes> prescricoesList;

    public UtilizadorNormal(String id, String name, String password, @Email String email) {
        super(id, name, password, email);
        this.bpmList = new LinkedList<>();
        this.pesagensList = new LinkedList<>();
        this.colestrolList = new LinkedList<>();
        this.outrosList = new LinkedList<>();
        this.dataInicio = new Date(System.currentTimeMillis());
    }


    public UtilizadorNormal() {

    }
}
