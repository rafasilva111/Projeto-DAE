package ejbs;


import entities.Pesagem;
import entities.UtilizadorNormal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Date;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private UtilizadorNormalBean utilizadorNormalBean;
    @EJB
    private ColestrolBean colestrolBean;
    @EJB
    private PesagemBean pesagemBean;
    @EJB
    private BPMBean bpmBean;


    @PostConstruct
    public void populateDB() {

        UtilizadorNormal utilzador1 = utilizadorNormalBean.create("rafael silva","rafa","rafa@gmail.com","rafa");
        UtilizadorNormal utilzador2 = utilizadorNormalBean.create("rafael francisco","rafa","rafa2@gmail.com","rafa2");

        colestrolBean.create(130 ,utilzador1.getId());
        colestrolBean.create(120 ,utilzador1.getId());
        colestrolBean.create(100 ,utilzador1.getId());

        pesagemBean.create( (float) 60, (float) 1.78,utilzador1.getId());
        pesagemBean.create((float) 97, (float) 1.88,utilzador1.getId());

        bpmBean.create(123,utilzador1.getId());
        bpmBean.create(250,utilzador1.getId());




        System.out.println();
    }
}

