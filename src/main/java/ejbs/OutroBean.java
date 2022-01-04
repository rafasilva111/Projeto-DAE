package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class OutroBean {
    @PersistenceContext
    private EntityManager em;

    public void create(Float value,String descricao, String utilizadorNormalID,String outroCatID){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);
        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }
        OutroCategories outroCategories =em.find(OutroCategories.class,outroCatID);
        try {
            if (outroCategories == null){
                throw new MyEntityNotFoundException("Categoria inserida nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }


        Outro outro = new Outro(outroCategories.getName(),value,utilizadorNormal,descricao,outroCategories);
        utilizadorNormal.addOutrosRegister(outro);
        em.persist(outro);
    };


    public List<Outro> getAllBpmRegisters(){
        return (List<Outro>) em.createNamedQuery("getAllOutroRegisters").getResultList();
    }

    public List<Outro> getBpmRegisters(){
        return (List<Outro>) em.createNamedQuery("getOutroRegisters").getResultList();
    }

    public Outro find(String id){
        Outro outro = em.find(Outro.class,id);

        if (outro.isDeleted()){
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id: "+id);
        }
        return outro;
    }

    public void update(String idOutro, SinalBiomedicoDTO sinalBiomedicoDTO) {
        Outro outro = em.find(Outro.class, idOutro);

        if(outro!=null){

            em.lock(outro, LockModeType.PESSIMISTIC_WRITE);
            if (sinalBiomedicoDTO.getUtilizadorNormalID() != null){
                UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
                if (utilizadorNormal== null){
                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+ sinalBiomedicoDTO.getUtilizadorNormalID());
                }
                outro.setUtilizadorNormal(utilizadorNormal);
            }

            if (sinalBiomedicoDTO.getDate() !=null){
                outro.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
            }
            if (sinalBiomedicoDTO.getDescricao()!=null){
                outro.setDescricao(sinalBiomedicoDTO.getDescricao());
            }


        }else
            throw new MyEntityNotFoundException("Registo de outro nao foi encontrado id:"+idOutro);

    }

    public void delete(String idOutro) {
        Outro outro = em.find(Outro.class, idOutro);
        OutroCategories outCat = em.find(OutroCategories.class,outro.getOutroCategories().getId());
        outCat.remove(outro);
        em.persist(outCat);
        UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class,outro.getUtilizadorNormal().getId());
        utilizadorNormal.remove(outro);
        em.persist(utilizadorNormal);

        outro.delete();
        if(outro!=null){
            em.persist(outro);
        }else
            throw new MyEntityNotFoundException("Registo de outro nao foi encontrado");
    }
}
