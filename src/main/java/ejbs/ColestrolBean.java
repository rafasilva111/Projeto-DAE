package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.UtilizadorNormal;
import entities.enums.Classification;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class ColestrolBean {
    @PersistenceContext
    private EntityManager em;

    public void create(float nivelColestrol, String utilizadorNormalID,String descricao){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);


        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }


        Colestrol colestrol = null;
        if (nivelColestrol<200) {
             colestrol = new Colestrol( nivelColestrol, utilizadorNormal, Classification.baixo, descricao);

        }
        if (nivelColestrol>=200 && nivelColestrol<=239){
             colestrol = new Colestrol( nivelColestrol, utilizadorNormal, Classification.medio, descricao);

        }
        if (nivelColestrol>240){
            colestrol = new Colestrol( nivelColestrol, utilizadorNormal, Classification.alto, descricao);

        }

        utilizadorNormal.addColestrolRegister(colestrol);
        em.persist(colestrol);
        System.out.println(colestrol.toString());
        em.persist(utilizadorNormal);
    };


    public List<Colestrol> getAllColestrol(){
        return (List<Colestrol>) em.createNamedQuery("getAllColestrolRegisters").getResultList();
    }

    public List<Colestrol> getColestrol(){
        return (List<Colestrol>) em.createNamedQuery("getColestrolRegisters").getResultList();
    }
    public Colestrol find(String id){
        Colestrol colestrol = em.find(Colestrol.class,id);
        if(colestrol == null){
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id: "+id);
        }
        if (colestrol.isDeleted()){
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id: "+id);
        }

        return colestrol;
    }

    public void update(String idColestrol, SinalBiomedicoDTO sinalBiomedicoDTO) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);


        if(colestrol!=null){

            em.lock(colestrol, LockModeType.PESSIMISTIC_WRITE);
            if (sinalBiomedicoDTO.getUtilizadorNormalID() != null){
                UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
                if (utilizadorNormal== null){
                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+ sinalBiomedicoDTO.getUtilizadorNormalID());
                }
                colestrol.setUtilizadorNormal(utilizadorNormal);
            }
            if (sinalBiomedicoDTO.getDescricao()!=null){
                colestrol.setDescricao(sinalBiomedicoDTO.getDescricao());
            }

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id:"+idColestrol);

    }

    public void delete(String idColestrol) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);
        colestrol.delete();
        UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class,colestrol.getUtilizadorNormal().getId());
        utilizadorNormal.remove(colestrol);
        em.persist(utilizadorNormal);

        if(colestrol!=null){
            em.persist(colestrol);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

    }

}
