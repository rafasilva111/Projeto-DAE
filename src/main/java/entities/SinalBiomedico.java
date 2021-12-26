package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@NamedQueries({
        @NamedQuery(
                name = "getAllBioSignals",
                query = "SELECT s FROM SinalBiomedico s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class SinalBiomedico implements Serializable {


    @Id
    private String id;
    private Date date;
    @ManyToOne
    private UtilizadorNormal utilizadorNormal;

    public SinalBiomedico() {
    }

    public SinalBiomedico(long id,UtilizadorNormal utilizadorNormal) {
        this.date= new Date(System.currentTimeMillis());
        this.id = id+"";
        this.utilizadorNormal = utilizadorNormal;
    }

    public UtilizadorNormal getUtilizadorNormal() {
        return utilizadorNormal;
    }

    public void setUtilizadorNormal(UtilizadorNormal utilizadorNormal) {
        this.utilizadorNormal = utilizadorNormal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SinalBiomedico{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", utilizadorNormal=" + utilizadorNormal +
                '}';
    }
}
