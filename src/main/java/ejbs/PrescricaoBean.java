package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.Doutor;
import entities.Prescricao;
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
public class PrescricaoBean {
    @PersistenceContext
    private EntityManager em;


    public void create(Date dataFim, Prescricao.TypePrescricoes tipo, String descricao, String utilizadorNormalId, String doutorId){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalId);


        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }

        Doutor doutor =em.find(Doutor.class,doutorId);

        try {
            if (doutor == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }


        Prescricao prescricao = new Prescricao(dataFim, tipo,descricao,utilizadorNormal,doutor);

        utilizadorNormal.addPrescicaoRegister(prescricao);
        doutor.addPrescicaoRegister(prescricao);
        em.persist(prescricao);

        em.persist(utilizadorNormal);
        em.persist(doutor);
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
            if (sinalBiomedicoDTO.getDescricao()!=null){
                colestrol.setDescricao(sinalBiomedicoDTO.getDescricao());
            }


        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id:"+idColestrol);

    }

    public void delete(String idColestrol) {
        Colestrol colestrol = em.find(Colestrol.class, idColestrol);
        UtilizadorNormal utilizador = em.find(UtilizadorNormal.class,colestrol.getUtilizadorNormal().getId());
        utilizador.remove(colestrol);

        if(colestrol!=null){
            em.detach(colestrol);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");

        int isSuccessful = em.createQuery("delete from SinalBiomedico p where p.id = :id ")
                .setParameter("id", idColestrol)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException(" product modified concurrently");
        }

    }
}