package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SinaisBiomedicos implements Serializable {


    @Id
    private Date date;

    public SinaisBiomedicos(Date date) {
        this.date = date;
    }

    public SinaisBiomedicos() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
