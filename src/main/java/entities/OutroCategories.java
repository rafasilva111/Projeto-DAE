package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class OutroCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private Float maxValues;
    private Float minValues;
    @OneToOne
    private Outro outro;

    public OutroCategories() {
    }
}
