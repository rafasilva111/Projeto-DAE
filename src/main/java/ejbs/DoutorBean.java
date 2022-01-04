package ejbs;

import entities.*;
import exceptions.MyEntityNotFoundException;

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
    public List<Doutor> getDoctors() {

        return (List<Doutor>) em.createNamedQuery("getDoctors").getResultList();

    }

    public Doutor getUserByUsername(String name){
        return (Doutor) em.createQuery("SELECT c FROM Doutor c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }

    public void delete(String id) {
        Doutor utilizador = em.find(Doutor.class, id);


        if (utilizador.getPrescricoes().size()!=0){
            for (Prescricao colestrol: utilizador.getPrescricoes()
            ) {
                Prescricao prescricao = em.find(Prescricao.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }

        if (utilizador.getPatients().size()!=0){
            for (UtilizadorNormal colestrol: utilizador.getPatients()
            ) {
                UtilizadorNormal prescricao = em.find(UtilizadorNormal.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }
        utilizador.delete();

        if(utilizador!=null){
            em.persist(utilizador);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

    }
}
