package entities;


import javax.persistence.*;

@Entity
public class Document {
    String filepath;
    String filename;

    @ManyToOne
    UtilizadorNormal utilizadorNormal;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    public Document() {}

    public Document(String filepath, String filename, UtilizadorNormal utilizador) {
        this.filepath = filepath;
        this.filename = filename;
        this.utilizadorNormal = utilizador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UtilizadorNormal getUtilizador() {
        return utilizadorNormal;
    }

    public void setUtilizador(UtilizadorNormal utilizador) {
        this.utilizadorNormal = utilizador;
    }
}
