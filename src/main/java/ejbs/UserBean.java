package ejbs;
import entities.BPM;
import entities.Utilizador;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    EntityManager em;
    public Utilizador authenticate(final String username, final String password) throws
            Exception {
        System.out.println(username);
        Utilizador user = getUserByUsername(username);
        if (user != null &&
                user.getPassword().equals(Utilizador.hashPassword(password))) {
            return user;
        }
        throw new Exception("Failed logging in with username '" + username + "':unknown username or wrong password");
    }

    public Utilizador getUserByUsername(String name){
        return (Utilizador) em.createQuery("SELECT c FROM Utilizador c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }


}
