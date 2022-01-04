package ejbs;

import entities.*;
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


        Doutor doc = em.find(Doutor.class,doutorId);

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
    public List<UtilizadorNormal> getNormalUsers() {

        return (List<UtilizadorNormal>) em.createNamedQuery("getNormUsers").getResultList();

    }

    public UtilizadorNormal getUserByUsername(String name){

        return (UtilizadorNormal) em.createQuery("SELECT c FROM UtilizadorNormal c WHERE c.userName LIKE ?1").setParameter(1, name).getSingleResult();
    }

    public void delete(String id) {
        UtilizadorNormal utilizador = em.find(UtilizadorNormal.class, id);

        if (utilizador.getColestrolList().size()!=0){
            for (Colestrol colestrol: utilizador.getColestrolList()
                 ) {
                Colestrol prescricao = em.find(Colestrol.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }
        if (utilizador.getPesagemList().size()!=0){
            for (Pesagem colestrol: utilizador.getPesagemList()
                 ) {
                Pesagem prescricao = em.find(Pesagem.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }if (utilizador.getBpmList().size()!=0){
            for (BPM colestrol: utilizador.getBpmList()
                 ) {
                BPM prescricao = em.find(BPM.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }if (utilizador.getOutrosList().size()!=0){
            for (Outro colestrol: utilizador.getOutrosList()
                 ) {
                Outro prescricao = em.find(Outro.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }
        if (utilizador.getPrescricoesList().size()!=0){
            for (Prescricao colestrol: utilizador.getPrescricoesList()
                 ) {
                Prescricao prescricao = em.find(Prescricao.class,colestrol.getId());
                prescricao.delete();
                em.persist(prescricao);
            }
        }

        Doutor doutor = em.find(Doutor.class,utilizador.getDoctor().getId());
        doutor.remove(utilizador);
        em.persist(doutor);
        utilizador.delete();
        if(utilizador!=null){
            em.persist(utilizador);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

    }



}
