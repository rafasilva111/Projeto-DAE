package ejbs;

import entities.Administrador;
import entities.Doutor;
import entities.Prescricao;
import entities.UtilizadorNormal;
import exceptions.MyEntityCantBeDeletedException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Email;
import java.util.List;

@Stateless
public class AdminBean {
    @PersistenceContext
    private EntityManager em;
    private boolean superUser;

    public Administrador create(String password, String email, String username){

        Administrador administrador = new Administrador(password,email,username);
        em.persist(administrador);

        return  administrador;
    };

    public Administrador find(String adminId){

        return em.find(Administrador.class,adminId);
    }

    public List<Administrador> getAllAdmins() {

        return (List<Administrador>) em.createNamedQuery("getAllAdmins").getResultList();
    }
    public List<Administrador> getAdmins() {

        return (List<Administrador>) em.createNamedQuery("getAdmins").getResultList();
    }



    public Administrador getUserByUsername(String name){
        return (Administrador) em.createQuery("SELECT c FROM Administrador c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }

    public void delete(String id) {
        Administrador utilizador = em.find(Administrador.class, id);
        if (utilizador.isSuperUser()){
            throw new MyEntityCantBeDeletedException("Esta entidade não pode ser apagada");
        }
        utilizador.delete();
        if(utilizador!=null){
            em.persist(utilizador);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

    }
}
