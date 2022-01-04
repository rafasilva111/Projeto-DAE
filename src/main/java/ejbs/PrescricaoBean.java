package ejbs;

import dtos.PrescricaoDTO;
import dtos.SinalBiomedicoDTO;
import entities.Colestrol;
import entities.Doutor;
import entities.Prescricao;
import entities.UtilizadorNormal;
import entities.enums.UserType;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class PrescricaoBean {
    @PersistenceContext
    private EntityManager em;


    public void create(Date dataFim, String tipo, String descricao, String utilizadorNormalId, String doutorId){

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


        Prescricao prescricao = new Prescricao(dataFim, Prescricao.TypePrescricoes.Exercicio ,descricao,utilizadorNormal,doutor);

        utilizadorNormal.addPrescicaoRegister(prescricao);
        doutor.addPrescicaoRegister(prescricao);
        em.persist(prescricao);

        em.persist(utilizadorNormal);
        em.persist(doutor);
    };


    public List<Prescricao> getAllPrescricoes(){
        return (List<Prescricao>) em.createNamedQuery("getAllPrescricoesRegisters").getResultList();
    }
    public List<Prescricao> getPrescricoes(){
        return (List<Prescricao>) em.createNamedQuery("getPrescricoesRegisters").getResultList();
    }
    public Prescricao find(String id){

        return em.find(Prescricao.class,id);
    }

    public void update(String idPrescricoes, PrescricaoDTO prescricao) {
        Prescricao colestrol = em.find(Prescricao.class, idPrescricoes);


        if(colestrol!=null){

            em.lock(colestrol, LockModeType.PESSIMISTIC_WRITE);

            if (prescricao.getDescricao()!=null){
                colestrol.setDescricao(prescricao.getDescricao());
            }
            if (prescricao.getDataFim()!=null){
                String[]  helper= null;
                if(prescricao.getDataFim().contains(" ")){
                    helper=prescricao.getDataFim().split(" ");
                }
                if(prescricao.getDataFim().contains("/")){
                    helper=prescricao.getDataFim().split("/");
                }

                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
                Date date = null;

                try{
                    date = ft.parse(helper[2]+"-"+helper[1]+"-"+helper[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                colestrol.setDataFim(date);
            }


        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado id:"+idPrescricoes);

    }

    public void delete(String idColestrol) {
        Prescricao prescricao = em.find(Prescricao.class, idColestrol);
        prescricao.delete();
        Doutor doutor = em.find(Doutor.class,prescricao.getDoutor().getId());
        doutor.remove(prescricao);
        em.persist(doutor);
        UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class,prescricao.getUtilizadorNormal().getId());
        utilizadorNormal.remove(prescricao);
        em.persist(utilizadorNormal);

        if(prescricao!=null){
            em.persist(prescricao);

        }else
            throw new MyEntityNotFoundException("Registo de prescricao nao foi encontrado");

    }

    public List<Prescricao> getUserByUsername(String id){
        return (List<Prescricao>) em.createQuery("SELECT c FROM Prescricao c WHERE c.doutor.id LIKE ?1").setParameter(1, id).getResultList();
    }

}