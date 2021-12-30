package ws;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import dtos.DoutorDTO;
import dtos.SinalBiomedicoDTO;
import dtos.UtilizadorDTO;
import ejbs.BioSinaisBean;
import ejbs.DoutorBean;
import ejbs.UserBean;
import ejbs.UtilizadorNormalBean;
import entities.Colestrol;
import entities.Doutor;
import entities.Utilizador;
import entities.enums.UserType;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UtilizadorNormalService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private UtilizadorNormalBean utilizadorNormalBean;
    @EJB
    private BioSinaisBean bioSinaisBean;
    @EJB
    private UserBean userBean;
    @EJB
    private DoutorBean doutorBean;

    private SinaisBiomedicosService helper;

    private UtilizadorDTO toDTOsemRegistos(UtilizadorNormal utilizadorNormal) {
        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal);
    }
    private UtilizadorDTO toDTOcomRegistos(UtilizadorNormal utilizadorNormal) {
        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal,preencher(utilizadorNormal)

                );
    }
    private DoutorDTO toDTOcomRegistosDoutor(Doutor utilizadorNormal) {
        PrescricoesService ps = new PrescricoesService();

        return new DoutorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.Doutor,
                ps.toDTOsPrescricoes(utilizadorNormal.getPrescricoes()),
                toDTOsUtilizadores(utilizadorNormal.getPatients() ));

    }
    protected List<UtilizadorDTO> toDTOsUtilizadores(List<UtilizadorNormal> students) {
        return students.stream().map(this::toDTOsemRegistos).collect(Collectors.toList());
    }

    private List<SinalBiomedicoDTO> preencher(UtilizadorNormal utilizadorNormal) {
        List<SinalBiomedicoDTO> list = new LinkedList<>();
        helper = new SinaisBiomedicosService();
        if (!utilizadorNormal.getColestrolList().isEmpty()) {

            for (Colestrol coletrol : utilizadorNormal.getColestrolList()
            ) {
                SinalBiomedicoDTO colestrolDTO = helper.toDTO(coletrol);
                list.add(colestrolDTO);

            }
        }
        if (!utilizadorNormal.getBpmList().isEmpty()) {
            for (var coletrol : utilizadorNormal.getBpmList()
            ) {
                list.add(helper.toDTO(coletrol));
            }

        }
        if (!utilizadorNormal.getPesagemList().isEmpty()) {
            for (var coletrol : utilizadorNormal.getPesagemList()
            ) {
                list.add(helper.toDTO(coletrol));
            }
        }
        if (!utilizadorNormal.getOutrosList().isEmpty()){
            for (var coletrol:utilizadorNormal.getOutrosList()
            ) {
                list.add(helper.toDTO(coletrol));
            }
        }

        return list;
    }




    @GET
    @Path("{username}")
    public Response getStudentDetails(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        //check 
        Principal principal = securityContext.getUserPrincipal();

        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("UtilizadorNormal")  &&
                        principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UtilizadorNormal student = utilizadorNormalBean.getUserByUsername(username);
        if(student == null) {
            throw new MyEntityNotFoundException("Student with username " +
                    username + " not found.");
        }
        return Response.status(Response.Status.OK)
                .entity(toDTOsemRegistos(student))
                .build();
    }

    @GET
    @Path("{username}/registers")
    public Response getStudentDetailsWhitRegisters(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        //check

        Principal principal = securityContext.getUserPrincipal();
        if (principal== null){
            System.out.println("problemas");
        }
        if(!(securityContext.isUserInRole("Administrator") || securityContext.isUserInRole("Doutor") ||
                securityContext.isUserInRole("UtilizadorNormal")  &&
                        principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        System.out.println("IM OUT MOTHERFUCKER");
        if(securityContext.isUserInRole("Doutor")){
            Doutor doutor = doutorBean.getUserByUsername(username);
            System.out.println("IM IN MOFO");
            return Response.status(Response.Status.OK)
                    .entity(toDTOcomRegistosDoutor(doutor))
                    .build();
        }
        UtilizadorNormal student = utilizadorNormalBean.getUserByUsername(username);
        if(student == null) {
            throw new MyEntityNotFoundException("Student with username " +
                    username + " not found.");

        }
        System.out.println(toDTOcomRegistos(student).toString());

        return Response.status(Response.Status.OK)
                .entity(toDTOcomRegistos(student))
                .build();
    }
}
