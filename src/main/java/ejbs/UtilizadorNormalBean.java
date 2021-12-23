package ejbs;

import entities.Utilizador;
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

    public UtilizadorNormal create( String name, String password, @Email String email,String username){
        int id = getAllNormalUsers().size();
        UtilizadorNormal utilizadorNormal = new UtilizadorNormal(id,name,password,email,username);
        em.persist(utilizadorNormal);
        return  utilizadorNormal;
    };

    public UtilizadorNormal find(String utilizadorNormalID){

        return em.find(UtilizadorNormal.class,utilizadorNormalID);
    }

    public List<UtilizadorNormal> getAllNormalUsers() {

        return (List<UtilizadorNormal>) em.createNamedQuery("getAllNormUsers").getResultList();

    }

    public UtilizadorNormal getUserByUsername(String name){
        return (UtilizadorNormal) em.createQuery("SELECT c FROM UtilizadorNormal c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }
}
