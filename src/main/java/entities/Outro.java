package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@NamedQueries({
        @NamedQuery(
                name = "getAllOutroRegisters",
                query = "SELECT s FROM Outro s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class Outro extends SinalBiomedico implements Serializable {

    private String name;
    private Float value;
    private float minValue;
    private float maxValue;
    @ManyToOne()
    private OutroCategories outroCategories;
    private Date date;

    public Outro(String name, Float value, float minValue, float maxValue) {
        super();
        this.name = name;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.outroCategories = new OutroCategories();

    }

    public Outro( String name, float value, float minValue, float maxValue,UtilizadorNormal utilizadorNormal) {
        super(utilizadorNormal);
        this.name = name;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
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

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
}
