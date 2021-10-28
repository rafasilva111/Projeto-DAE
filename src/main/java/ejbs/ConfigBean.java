package ejbs;


import entities.UtilizadorNormal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
@Startup
@Singleton
public class ConfigBean {

    @EJB
    private UtilizadorNormalBean utilizadorNormalBean;


    @PostConstruct
    public void populateDB() {

        utilizadorNormalBean.create("1","rafa","rafa","rafa@gmail.com");


        System.out.println("Hello Java EE");
    }
}

