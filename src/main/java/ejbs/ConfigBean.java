package ejbs;


import entities.Doutor;
import entities.Pesagem;
import entities.Prescricao;
import entities.UtilizadorNormal;

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


    @PostConstruct
    public void populateDB() {

        UtilizadorNormal utilzador1 = utilizadorNormalBean.create("rafa","rafa@gmail.com","rafael",null);
        UtilizadorNormal utilzador2 = utilizadorNormalBean.create("rafa","rafa2@gmail.com","rafael silva",null);
        Doutor doutor = doutorBean.create("rafa","doctor@gmail.com","rafaelferreira");


        colestrolBean.create(130 ,utilzador1.getId(),"foi interessante");
        colestrolBean.create(120 ,utilzador1.getId(),"");
        colestrolBean.create(100 ,utilzador1.getId(),"não foi interessante");


        pesagemBean.create( (float) 60, (float) 1.78,utilzador1.getId(),"");
        pesagemBean.create((float) 97, (float) 1.88,utilzador1.getId(),"");

        bpmBean.create(123,utilzador1.getId());
        bpmBean.create(250,utilzador1.getId());
        Date dateFim = null;
        try {
            dateFim =new SimpleDateFormat("dd/MM/yyyy").parse("29/12/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        prescricaoBean.create(dateFim, Prescricao.TypePrescricoes.Medica,"Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());
        prescricaoBean.create(dateFim, Prescricao.TypePrescricoes.Medica,"Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());

        Date dateFim2 = null;
        try {
            dateFim2 =new SimpleDateFormat("dd/MM/yyyy").parse("29/12/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
       prescricaoBean.create(dateFim, Prescricao.TypePrescricoes.Exercicio,"Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());
        prescricaoBean.create(dateFim2, Prescricao.TypePrescricoes.Medica,"Tens de comar x comprimidos",utilzador1.getId(),doutor.getId());

        System.out.println();
    }
}

