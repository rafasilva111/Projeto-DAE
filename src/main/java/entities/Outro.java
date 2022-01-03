package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@NamedQueries({
        @NamedQuery(
                name = "getOutroRegisters",
                query = "SELECT s FROM Outro s WHERE S.deleted = FALSE ORDER BY s.id  " // JPQL
        ),
        @NamedQuery(
                name = "getAllOutroRegisters",
                query = "SELECT s FROM Outro s ORDER BY s.id  " // JPQL
        ),
})
@Entity
public class Outro extends SinalBiomedico implements Serializable {

    private String name;
    private Float value;
    @ManyToOne()
    private OutroCategories outroCategories;

    public Outro( String name, float value,UtilizadorNormal utilizadorNormal,String descricao,OutroCategories outroCategories) {
        super(utilizadorNormal,descricao);
        this.name = name;
        this.value = value;
        this.outroCategories = outroCategories;
    }

    public Outro() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
    public void setValue(String value) {
        this.value = Float.parseFloat(value);
    }


    public OutroCategories getOutroCategories() {
        return outroCategories;
    }

    public void setOutroCategories(OutroCategories outroCategories) {
        this.outroCategories = outroCategories;
    }
}
