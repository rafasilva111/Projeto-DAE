package ejbs;


import entities.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @EJB
    private DoutorBean doutorBean;
    @EJB
    private PrescricaoBean prescricaoBean;
    @EJB
    private AdminBean adminBean;
    @EJB
    private OutroBean outroBean;
    @EJB
    private OutroCategoriesBean outroCategoriesBean;


    @PostConstruct
    public void populateDB() {

        Doutor doutor = doutorBean.create("rafa","doctorR@gmail.com","rafaelferreira");
        Doutor doutor2 = doutorBean.create("lucas","doctorL@gmail.com","lucassantos");
        UtilizadorNormal utilzador1 = utilizadorNormalBean.create("rafael","rafa","rafa@gmail.com",doutor.getId());
        UtilizadorNormal utilzador2 = utilizadorNormalBean.create("rafaelsilva","rafa","rafa2@gmail.com",doutor.getId());
        UtilizadorNormal utilzador3 = utilizadorNormalBean.create("lucas","lucas","lucas@gmail.com",doutor.getId());
        UtilizadorNormal utilzador4 = utilizadorNormalBean.create("lucas2","lucas","lucas2@gmail.com",doutor.getId());
        Administrador administrador = adminBean.create("lucas","adminL@gmail.com","lucasbastos");
        Administrador administrador2 = adminBean.create("rafa","adminR@gmail.com","rafaelfrancisco");

        administrador2.setSuperUser(true);
        colestrolBean.create(130 ,utilzador1.getId(),"foi interessante");
        colestrolBean.create(120 ,utilzador1.getId(),"");
        colestrolBean.create(100 ,utilzador1.getId(),"não foi interessante");


        pesagemBean.create( (float) 60, (float) 1.78,utilzador1.getId(),"");
        pesagemBean.create((float) 97, (float) 1.88,utilzador1.getId(),"");

        bpmBean.create(123,"Bues de batimentos",utilzador1.getId());
        bpmBean.create(250,"Depois de um sprint",utilzador1.getId());
        Date dateFim = null;
        try {
            dateFim =new SimpleDateFormat("dd/MM/yyyy").parse("29/12/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        prescricaoBean.create(dateFim, "","Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());
        prescricaoBean.create(dateFim, "","Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());

        Date dateFim2 = null;
        try {
            dateFim2 =new SimpleDateFormat("dd/MM/yyyy").parse("29/12/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        prescricaoBean.create(dateFim, "","Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());
        prescricaoBean.create(dateFim2, "","Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());

        OutroCategories outroCat = outroCategoriesBean.create("Temperatura Corporal",(float) 14.1,(float) 12.1);

        OutroCategories outroCat2 = outroCategoriesBean.create("Numero de unhas cortadas",(float) 0,(float) 21);


        outroBean.create((float) 123,"curti",utilzador1.getId(),outroCat.getId());
        outroBean.create((float) 21,"não curti",utilzador1.getId(),outroCat2.getId());
        outroBean.create((float) 12,"curti",utilzador1.getId(),outroCat.getId());


    }
}

