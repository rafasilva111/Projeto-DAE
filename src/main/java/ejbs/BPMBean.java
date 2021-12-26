package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.BPM;
import entities.Colestrol;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class BPMBean {
    @PersistenceContext
    private EntityManager em;

    public void create(float batimentos, String utilizadorNormalID){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);


        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }
        int count = getAllBPM().size();
        BPM utilizadorN = new BPM(count+1000,Math.round(batimentos),utilizadorNormal);
        utilizadorNormal.addBpmRegister(utilizadorN);
        em.persist(utilizadorN);
    };


    public List<BPM> getAllBPM(){
        return (List<BPM>) em.createNamedQuery("getAllBPMRegisters").getResultList();
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
                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+sinalBiomedicoDTO.getUtilizadorNormalID());
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
