package ejbs;

import dtos.SinalBiomedicoDTO;
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
public class PesagemBean {
    @PersistenceContext
    private EntityManager em;

    public void create( float peso, float altura, String utilizadorNormalID,String descricao){

        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizadorNormalID);
        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }

        int id = getAllPesagens().size();
        float imc = peso/(altura*altura);
        Classification classification=null;

        if (imc<18.5){
            classification = Classification.baixo;
        }if (imc>=18.5&&imc<=24.9){
            classification = Classification.medio;
        }
        if (imc>24.9&& imc<=30){
            classification = Classification.alto;
        }
        if(imc>30) {
            classification = Classification.muitoalto;
        }

        System.out.println("hello"+ classification);
        Pesagem pesagem = new Pesagem( imc,peso,classification,descricao, altura,utilizadorNormal);
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
            em.lock(pesagem, LockModeType.PESSIMISTIC_WRITE);

            if (sinalBiomedicoDTO.getUtilizadorNormalID()!= null){
                UtilizadorNormal utilizadorNormal = em.find(UtilizadorNormal.class, sinalBiomedicoDTO.getUtilizadorNormalID());
                if (utilizadorNormal == null){

                    throw new MyEntityNotFoundException("Utilizador nao foi encontrado id:"+ sinalBiomedicoDTO.getUtilizadorNormalID());

                }
                pesagem.setUtilizadorNormal(utilizadorNormal);

            }

            if (sinalBiomedicoDTO.getDescricao()!=null){
                pesagem.setDescricao(sinalBiomedicoDTO.getDescricao());
            }

            if (sinalBiomedicoDTO.getDate() !=null){
                pesagem.setDate(new Date(Long.parseLong(sinalBiomedicoDTO.getDate())));
            }

            if (sinalBiomedicoDTO.getValue() !=null){
                pesagem.setAltura(Float.parseFloat(sinalBiomedicoDTO.getValue().get(0)));
                pesagem.setPeso(Float.parseFloat(sinalBiomedicoDTO.getValue().get(1)));
            }


        }else
            throw new MyEntityNotFoundException("Registo de Pesagem nao foi encontrado id:"+id);
    }

    public void delete(String idPesagem) {
        Pesagem pesagem = em.find(Pesagem.class, idPesagem);
        UtilizadorNormal utilizador = em.find(UtilizadorNormal.class,pesagem.getUtilizadorNormal().getId());
        utilizador.remove(pesagem);

        int isSuccessful = em.createQuery("delete from Pesagem p where p.id = :id ")
                .setParameter("id", idPesagem)
                .executeUpdate();

        if(pesagem!=null){
            em.detach(pesagem);

        }else
            throw new MyEntityNotFoundException("Registo de colestrol nao foi encontrado");


        if (isSuccessful == 0) {
            throw new OptimisticLockException(" product modified concurrently");
        }
        }
}
