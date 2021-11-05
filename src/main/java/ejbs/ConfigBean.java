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


    @PostConstruct
    public void populateDB() {

        UtilizadorNormal utilzador1 = utilizadorNormalBean.create("rafa","rafa","rafa@gmail.com");
        UtilizadorNormal utilzador2 = utilizadorNormalBean.create("rafa2","rafa","rafa2@gmail.com");

        colestrolBean.create(130 ,utilzador1.getId());
        colestrolBean.create(120 ,utilzador1.getId());
        colestrolBean.create(100 ,utilzador1.getId());

        pesagemBean.create( (float) 60, (float) 1.78,utilzador1.getId());
        pesagemBean.create((float) 60, (float) 1.78,utilzador1.getId());
        pesagemBean.create( (float) 60, (float) 1.78,utilzador1.getId());

        System.out.println("Hello Java EE");
    }
}

