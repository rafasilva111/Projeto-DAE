package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
@NamedQueries({
        @NamedQuery(
                name = "getAllAdmins",
                query = "SELECT s FROM Administrador s ORDER BY s.id" // JPQL
        ),
})
@Entity
public class Administrador extends Utilizador implements Serializable {
    private boolean superUser= false;

    public Administrador(String password, @Email String email, String userName) {
        super(password, email, userName);

    }
    public Administrador() {

    }

    public boolean isSuperUser() {
        return superUser;
    }
}
