package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SinalBiomedico implements Serializable {


    @Id
    private String id;
    private Date date;

    public SinalBiomedico() {
        this.date = new Date(System.currentTimeMillis());
        this.id = date.getTime()+"";
    }

    public SinalBiomedico(long id) {
        this.date= new Date(System.currentTimeMillis());
        this.id = id+"";
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
}
