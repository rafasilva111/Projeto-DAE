package ejbs;

import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.Pesagem;
import entities.SinalBiomedico;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class PesagemBean {
    @PersistenceContext
    private EntityManager em;

    public void create( float peso, float altura, String utilizadorNormalID){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);
        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }

        int id = getAllPesagens().size();
        Pesagem pesagem = new Pesagem(id+100, peso, altura,utilizadorNormal);
        utilizadorNormal.addPesagemRegister(pesagem);
        em.persist(pesagem);
    };




    public List<Pesagem> getAllPesagens(){
        return (List<Pesagem>) em.createNamedQuery("getAllPesagemRegisters").getResultList();
    }
    public Pesagem find(String id){

        return em.find(Pesagem.class,id);
    }

    public void update(String id, SinalBiomedicoDTO sinalBiomedicoDTO) {
        Pesagem pesagem = em.find(Pesagem.class, id);
        if(pesagem!=null){
            UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
            if(utilizadorNormal!=null) {
                em.lock(pesagem, LockModeType.PESSIMISTIC_WRITE);
                pesagem.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
                pesagem.setAltura(sinalBiomedicoDTO.getValue().get(0));
                pesagem.setPeso(sinalBiomedicoDTO.getValue().get(1));
                pesagem.setUtilizadorNormal(utilizadorNormal);
            }else
                throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+sinalBiomedicoDTO.getUtilizadorNormalID());
        }else
            throw new MyEntityNotFoundException("Registo de Pesagem nao foi encontrado id:"+id);
    }

    public void delete(String idPesagem) {
        Pesagem pesagem = em.find(Pesagem.class, idPesagem);
        if(pesagem!=null){
            em.detach(pesagem);
        }else
            throw new MyEntityNotFoundException("Registo de Pesagem nao foi encontrado id:"+idPesagem);
    }
}
