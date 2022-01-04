package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.BPM;
import entities.Colestrol;
import entities.Pesagem;
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
public class BPMBean {
    @PersistenceContext
    private EntityManager em;

    public void create(float batimentos, String descricao, String utilizadorNormalID){
        Classification classification = Classification.muitobaixo;
        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);

        if (batimentos<60){
            classification = Classification.baixo;
        }
        if (batimentos>=60&& batimentos<=100){
            classification = Classification.medio;
        }if (batimentos>100){
            classification = Classification.alto;
        }
        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }

        BPM utilizadorN = new BPM(Math.round(batimentos),utilizadorNormal,classification,descricao);
        utilizadorNormal.addBpmRegister(utilizadorN);
        em.persist(utilizadorN);
    };

    public List<BPM> getAllBpmRegisters(){
        return (List<BPM>) em.createNamedQuery("getAllBpmRegisters").getResultList();
    }

    public List<BPM> getBpmRegisters(){
        return (List<BPM>) em.createNamedQuery("getBpmRegisters").getResultList();
    } public BPM find(String id){
        BPM colestrol = em.find(BPM.class,id);
        if (colestrol.isDeleted()){
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id: "+id);
        }
        return colestrol;
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
                bpm.setNumeroBatimentos(Integer.parseInt(sinalBiomedicoDTO.getValue().get(0)));
            }
            if (sinalBiomedicoDTO.getDescricao()!=null){
                bpm.setDescricao(sinalBiomedicoDTO.getDescricao());
            }


        }else
            throw new MyEntityNotFoundException("Registo de BPM nao foi encontrado id:"+idBPM);

    }

    public void delete(String idBPM) {
        BPM colestrol = em.find(BPM.class, idBPM);

        UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class,colestrol.getUtilizadorNormal().getId());
        utilizadorNormal.remove(colestrol);
        em.persist(utilizadorNormal);

        if(colestrol!=null){
            em.persist(colestrol);
            colestrol.delete();
        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

    }
}
