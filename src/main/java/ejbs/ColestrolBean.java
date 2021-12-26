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
        Colestrol colestrol = new Colestrol(count+10,nivelColestrol,utilizadorNormal);
        utilizadorNormal.addColestrolRegister(colestrol);
        em.persist(colestrol);
        System.out.println(colestrol.toString());
        em.persist(utilizadorNormal);
    };


    public List<Colestrol> getAllColestrol(){
        return (List<Colestrol>) em.createNamedQuery("getAllColestrolRegisters").getResultList();
    }
    public Colestrol find(String id){

        return em.find(Colestrol.class,id);
    }

    public void update(String idColestrol, SinalBiomedicoDTO sinalBiomedicoDTO) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);


        if(colestrol!=null){

            em.lock(colestrol, LockModeType.PESSIMISTIC_WRITE);
            if (sinalBiomedicoDTO.getUtilizadorNormalID() != null){
                UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
                if (utilizadorNormal== null){
                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+sinalBiomedicoDTO.getUtilizadorNormalID());
                }
                colestrol.setUtilizadorNormal(utilizadorNormal);
            }

            if (sinalBiomedicoDTO.getDate() !=null){
                colestrol.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
            }

            if (sinalBiomedicoDTO.getValue()!=null){
                colestrol.setNivelColestrol(sinalBiomedicoDTO.getValue().get(0));
            }


        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id:"+idColestrol);

    }

    public void delete(String idColestrol) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);
        if(colestrol!=null){
            em.detach(colestrol);
        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");
    }
}
