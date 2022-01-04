package ejbs;

import entities.Doutor;
import entities.Pesagem;
import entities.Utilizador;
import entities.UtilizadorNormal;
import entities.enums.Classification;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Email;
import java.util.List;

@Stateless
public class UtilizadorNormalBean {
    @PersistenceContext
    private EntityManager em;

    public UtilizadorNormal create( String username,String password,String email, String doutorId){

        System.out.println(doutorId);
        Doutor doc = em.find(Doutor.class,doutorId);
        System.out.println(doc);
        System.out.println("Dude");
        UtilizadorNormal utilizadorNormal = new UtilizadorNormal(password,email,username,doc);
        em.persist(utilizadorNormal);

        doc.addPatientsRegister(utilizadorNormal);
        em.persist(doc);

        return  utilizadorNormal;
    };

    public UtilizadorNormal find(String utilizadorNormalID){

        return em.find(UtilizadorNormal.class,utilizadorNormalID);
    }

    public List<UtilizadorNormal> getAllNormalUsers() {

        return (List<UtilizadorNormal>) em.createNamedQuery("getAllNormUsers").getResultList();

    }

    public UtilizadorNormal getUserByUsername(String name){
        System.out.println("aqui"+ name);
        return (UtilizadorNormal) em.createQuery("SELECT c FROM UtilizadorNormal c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }



}
