package dtos;

import entities.Outro;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class OutroCategoriesDTO implements Serializable {

    private String id;
    private String name;
    private Float maxValues;
    private Float minValues;
    private List<SinalBiomedicoOutroDTO> outro;
    private String date;

    public OutroCategoriesDTO(String id,Date data, String name, Float maxValues, Float minValues, List<SinalBiomedicoOutroDTO> outro) {
        this.id = id;
        this.name = name;
        this.maxValues = maxValues;
        this.minValues = minValues;
        this.outro = outro;
        this.date = new SimpleDateFormat("dd/MM/yyyy kk:mm").format(data);
    }

    public OutroCategoriesDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

}
