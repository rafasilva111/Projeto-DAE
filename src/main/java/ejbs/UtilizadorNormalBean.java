package ejbs;

import entities.UtilizadorNormal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Email;

@Stateless
public class UtilizadorNormalBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String id, String name, String password, @Email String email){
        UtilizadorNormal utilizadorNormal = new UtilizadorNormal(id,name,password,email);
        em.persist(utilizadorNormal);
    };
}
