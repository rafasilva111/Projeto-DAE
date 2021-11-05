package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
public class Utilizador implements Serializable {
    @Id
    private String id;
    private String name;
    private String password;
    @Email
    private String email;


    public Utilizador( String name, String password, @Email String email) {
        this.id = System.currentTimeMillis()+"";
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Utilizador() {
    }

    public Utilizador(int id, String name, String password, String email) {
        this.id = id+"";
        this.name = name;
        this.password = password;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
