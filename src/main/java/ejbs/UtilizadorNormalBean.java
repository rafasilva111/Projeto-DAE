package ejbs;

import entities.UtilizadorNormal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Email;
import java.util.List;

@Stateless
public class UtilizadorNormalBean {
    @PersistenceContext
    private EntityManager em;

    public UtilizadorNormal create( String name, String password, @Email String email){
        int id = getAllNormalUsers().size();
        UtilizadorNormal utilizadorNormal = new UtilizadorNormal(id,name,password,email);
        em.persist(utilizadorNormal);
        return  utilizadorNormal;
    };

    public UtilizadorNormal find(String utilizadorNormalID){

        return em.find(UtilizadorNormal.class,utilizadorNormalID);
    }

    public List<UtilizadorNormal> getAllNormalUsers() {
        // remember, maps to: “SELECT s FROM Subject s ORDER BY s.name”
        return (List<UtilizadorNormal>) em.createNamedQuery("getAllNormUsers").getResultList();

    }
}
