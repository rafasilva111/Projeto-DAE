package ejbs;

import dtos.OutroCategoriesDTO;
import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.Outro;
import entities.OutroCategories;
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
public class OutroCategoriesBean {
    @PersistenceContext
    private EntityManager em;

    public OutroCategories create(String name, Float maxValues, Float minValues){
        OutroCategories outro = new OutroCategories(name,maxValues,minValues);
        em.persist(outro);
        return outro;
    };


    public List<OutroCategories> getAllCatagories(){
        return (List<OutroCategories>) em.createNamedQuery("getAllOutroCatRegisters").getResultList();
    }
    public OutroCategories find(String id){

        return em.find(OutroCategories.class,id);
    }

    public void update(String idOutroCat, OutroCategoriesDTO sinalBiomedicoDTO) {
        OutroCategories outroCategories = em.find(OutroCategories.class, idOutroCat);


        if(outroCategories!=null){

            em.lock(outroCategories, LockModeType.PESSIMISTIC_WRITE);

            if (sinalBiomedicoDTO.getName()!=null){
                outroCategories.setName(sinalBiomedicoDTO.getName());
            }
            if (sinalBiomedicoDTO.getMinValues()!=null){
                outroCategories.setMaxValues(sinalBiomedicoDTO.getMaxValues());
            }
            if (sinalBiomedicoDTO.getMaxValues()!= null){
                outroCategories.setMinValues((sinalBiomedicoDTO.getMinValues()));
            }



        }else
            throw new MyEntityNotFoundException("Registo de categories nao foi encontrado id:"+idOutroCat);

    }

    public void delete(String idColestrol) {
        OutroCategories colestrol = em.find(OutroCategories.class, idColestrol+"here");



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
