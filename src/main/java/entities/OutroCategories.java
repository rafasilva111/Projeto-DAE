package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@NamedQueries({
        @NamedQuery(
                name = "getAllOutroCatRegisters",
                query = "SELECT s FROM OutroCategories s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class OutroCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private Float maxValues;
    private Float minValues;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "outroCategories")
    private List<Outro> outro;
    private Date date;

    public OutroCategories() {
    }

    public OutroCategories(String name, Float maxValues, Float minValues) {
        this.name = name;
        this.maxValues = maxValues;
        this.minValues = minValues;
        this.outro = new LinkedList<>();
        this.date = new Date();
    }

    public void setOutro(List<Outro> outro) {
        this.outro = outro;
    }

    public void addOutro(Outro outro){
        this.outro.add(outro);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(Float maxValues) {
        this.maxValues = maxValues;
    }

    public Float getMinValues() {
        return minValues;
    }

    public void setMinValues(Float minValues) {
        this.minValues = minValues;
    }

    public List<Outro> getOutro() {
        return outro;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public void remove(Outro outro) {

        if (this.outro.contains(outro)){
            List<Outro> outros = this.getOutro();
            outros.remove(outro);
            this.outro = outros;
        }

    }
}
