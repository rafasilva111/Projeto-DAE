package ejbs;

import entities.Doutor;
import entities.UtilizadorNormal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Email;
import java.util.List;

@Stateless
public class DoutorBean {
    @PersistenceContext
    private EntityManager em;

    public Doutor create(String password, String email, String username){
        System.out.println("here here");
        Doutor doutor = new Doutor(password,email,username);
        em.persist(doutor);
        return  doutor;
    };

    public Doutor find(String doutorId){

        return em.find(Doutor.class,doutorId);
    }

    public List<Doutor> getAllDoctors() {

        return (List<Doutor>) em.createNamedQuery("getAllDoctors").getResultList();

    }

    public Doutor getUserByUsername(String name){
        return (Doutor) em.createQuery("SELECT c FROM Doutor c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }
}
