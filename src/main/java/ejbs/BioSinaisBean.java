package ejbs;


import dtos.SinalBiomedicoDTO;
import entities.BPM;
import entities.SinalBiomedico;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class BioSinaisBean {
    @PersistenceContext
    private EntityManager em;


    public List<SinalBiomedico> getAllBioSignals(){
        return (List<SinalBiomedico>) em.createNamedQuery("getAllBioSignals").getResultList();
    }

    public List<SinalBiomedico> getAllBioSignalsByUsername(String name){
        return (List<SinalBiomedico>) em.createQuery("SELECT c FROM SinalBiomedico c WHERE c.utilizadorNormal.userName LIKE ?1").setParameter(1, name).getResultList();
    }
    public BPM find(String id){

        return em.find(BPM.class,id);
    }

    public void update(String idBPM, SinalBiomedicoDTO sinalBiomedicoDTO) {
        BPM bpm = em.find(BPM.class, idBPM);


        if(bpm!=null){

            em.lock(bpm, LockModeType.PESSIMISTIC_WRITE);
            if (sinalBiomedicoDTO.getUtilizadorNormalID() != null){
                UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
                if (utilizadorNormal== null){
                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+ sinalBiomedicoDTO.getUtilizadorNormalID());
                }
                bpm.setUtilizadorNormal(utilizadorNormal);
            }

            if (sinalBiomedicoDTO.getDate() !=null){
                bpm.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
            }

            if (sinalBiomedicoDTO.getValue()!=null){
                bpm.setNumeroBatimentos(Math.round(sinalBiomedicoDTO.getValue().get(0)));
            }


        }else
            throw new MyEntityNotFoundException("Registo de BPM nao foi encontrado id:"+idBPM);

    }

    public void delete(String idBPM) {
        BPM bpm = em.find(BPM.class, idBPM);
        if(bpm!=null){
            em.detach(bpm);
        }else
            throw new MyEntityNotFoundException("Registo de BPM nao foi encontrado");
    }
}