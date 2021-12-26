package ws;

import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import dtos.SinalBiomedicoDTO;
import dtos.UtilizadorDTO;
import ejbs.BioSinaisBean;
import ejbs.UserBean;
import ejbs.UtilizadorNormalBean;
import entities.Colestrol;
import entities.SinalBiomedico;
import entities.UserType;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

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

    private SinaisBiomedicosService helper;

    private UtilizadorDTO toDTOsemRegistos(UtilizadorNormal utilizadorNormal) {
        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getName(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal);
    }
    private UtilizadorDTO toDTOcomRegistos(UtilizadorNormal utilizadorNormal) {
        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getName(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal,preencher(utilizadorNormal)

                );
    }

    private List<SinalBiomedicoDTO> preencher(UtilizadorNormal utilizadorNormal) {
        List<SinalBiomedicoDTO> list = new LinkedList<>();
        for (var coletrol:utilizadorNormal.getColestrolList()
             ) {
            list.add(helper.toDTO(coletrol));
        }
        for (var coletrol:utilizadorNormal.getBpmList()
        ) {
            list.add(helper.toDTO(coletrol));
        }
        for (var coletrol:utilizadorNormal.getPesagemList()
        ) {
            list.add(helper.toDTO(coletrol));
        }
        for (var coletrol:utilizadorNormal.getOutrosList()
        ) {
            list.add(helper.toDTO(coletrol));
        }
        return list;
    }


    @GET
    @Path("{username}")
    public Response getStudentDetails(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        //check 
        Principal principal = securityContext.getUserPrincipal();
        if (principal== null){
            System.out.println("problemas");
        }


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
                .entity(toDTOcomRegistos(student))
                .build();
    }
}
