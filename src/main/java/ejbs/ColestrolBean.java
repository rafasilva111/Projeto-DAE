package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.SinalBiomedico;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;
import jdk.jshell.execution.Util;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import java.util.Date;
import java.util.List;

@Stateless
public class ColestrolBean {
    @PersistenceContext
    private EntityManager em;

    public void create(float nivelColestrol, String utilizadorNormalID){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);


        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }
        int count = getAllColestrol().size();
        Colestrol utilizadorN = new Colestrol(count+10,nivelColestrol,utilizadorNormal);
        utilizadorNormal.addColestrolRegister(utilizadorN);
        em.persist(utilizadorN);
    };


    public List<Colestrol> getAllColestrol(){
        return (List<Colestrol>) em.createNamedQuery("getAllColestrolRegisters").getResultList();
    }
    public Colestrol find(String id){

        return em.find(Colestrol.class,id);
    }

    public void update(String idColestrol, SinalBiomedicoDTO sinalBiomedicoDTO) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);
        try{


        if(colestrol!=null){
            UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
            if(utilizadorNormal!=null) {
                em.lock(colestrol, LockModeType.PESSIMISTIC_WRITE);
                colestrol.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
                colestrol.setNivelColestrol((float) sinalBiomedicoDTO.getValue().get(0));
                colestrol.setUtilizadorNormal(utilizadorNormal);
            }else
                throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+sinalBiomedicoDTO.getUtilizadorNormalID());
        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id:"+idColestrol);
        }catch (NumberFormatException e){
            throw new NumberFormatException(new StringBuilder().append(sinalBiomedicoDTO.getValue().get(0)).append("/").append(sinalBiomedicoDTO.getValue().size()).toString());
        }
    }

    public void delete(String idColestrol) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);
        if(colestrol!=null){
            em.detach(colestrol);
        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");
    }
}
