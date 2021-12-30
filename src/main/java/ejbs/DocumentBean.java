package ejbs;

import entities.Document;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DocumentBean {
    @PersistenceContext
    EntityManager em;
    @EJB
    UtilizadorNormalBean utilizadorNormalBean;

    public void create(UtilizadorNormal utilizador, String filepath, String filename) {
        Document document = new Document(filepath, filename, utilizador);
        UtilizadorNormal utilizadorNormal =em.find(UtilizadorNormal.class,utilizador.getId());

        try {
            if (utilizadorNormal == null){
                throw new MyEntityNotFoundException("Utilizador inserido nao existe");
            }
        }catch (MyEntityNotFoundException e){
            System.err.print(e.getMessage());
        }

        utilizadorNormal.addDocument(document);
        em.persist(utilizadorNormal);
        System.out.println("here"+ utilizadorNormal.getDocuments());
        em.persist(document);
    }

    public Document findDocument(int id){
        return em.find(Document.class, id);
    }

    public List<Document> getStudentDocuments(String username) {
        utilizadorNormalBean = new UtilizadorNormalBean();

 UtilizadorNormal utilizadorNormal = utilizadorNormalBean.getUserByUsername(username);
        System.out.println("aqui"+utilizadorNormal.toString());
        return  (List<Document>) em.createQuery("SELECT doc FROM Document doc WHERE doc.utilizadorNormal.id = :username").setParameter("username", utilizadorNormal.getId()).getResultList();

    }
}
