package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.Outro;
import entities.OutroCategories;
import entities.UtilizadorNormal;
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


    public List<Outro> getAllOutros(){
        return (List<Outro>) em.createNamedQuery("getAllOutroRegisters").getResultList();
    }
    public Outro find(String id){

        return em.find(Outro.class,id);
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

            if (sinalBiomedicoDTO.getValue()!=null){
                outro.setValue(sinalBiomedicoDTO.getValue().get(0));
            }
            if (sinalBiomedicoDTO.getDescricao()!=null){
                outro.setValue(sinalBiomedicoDTO.getDescricao());
            }


        }else
            throw new MyEntityNotFoundException("Registo de outro nao foi encontrado id:"+idOutro);

    }

    public void delete(String idOutro) {
        Outro colestrol = em.find(Outro.class, idOutro);
        if(colestrol!=null){
            em.detach(colestrol);
        }else
            throw new MyEntityNotFoundException("Registo de outro nao foi encontrado");
    }
}
