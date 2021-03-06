package ws;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import dtos.*;
import ejbs.*;
import entities.*;
import entities.enums.UserType;
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
    @EJB
    private AdminBean adminBean;

    private SinaisBiomedicosService helper;

    private UtilizadorDTO toDTOsemRegistos(UtilizadorNormal utilizadorNormal) {
        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal,
                utilizadorNormal.getDoctor()==null ?"":utilizadorNormal.getDoctor().getUserName(),utilizadorNormal.getDoctor().getId());

    }
    private UtilizadorDTO toDTOcomRegistos(UtilizadorNormal utilizadorNormal) {
        //todo needs to be implemeted agaian, is calling empty construct

        return new UtilizadorDTO(
                utilizadorNormal.getId(),
                utilizadorNormal.getPassword(),
                utilizadorNormal.getEmail(),
                utilizadorNormal.getData(),
                utilizadorNormal.getUserName(),
                UserType.UtilizadorNormal,
                utilizadorNormal.getDoctor()==null ?"":utilizadorNormal.getDoctor().getUserName(),utilizadorNormal.getDoctor().getId());

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
    @GET
    @Path("doc/{id}")
    public Response getUserDoc(@PathParam("id") String id){

        Doutor utilizadorNormal = doutorBean.getUserByUsername(id);
        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOcomRegistosDoutor(utilizadorNormal))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_USER_RECORD")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id){

        UtilizadorNormal utilizadorNormal = utilizadorNormalBean.getUserByUsername(id);
        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsemRegistos(utilizadorNormal))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_USER_RECORD")
                .build();
    }
    private AdministradorDTO toDTOcomRegistosAdministrador(Administrador administrador) {

        return new AdministradorDTO(
                administrador.getId(),
                administrador.getPassword(),
                administrador.getEmail(),
                administrador.getData(),
                administrador.getUserName(),
                administrador.isSuperUser());

    }


    protected List<UtilizadorDTO> toDTOsUtilizadores(List<UtilizadorNormal> students) {
        return students.stream().map(this::toDTOsemRegistos).collect(Collectors.toList());
    }



    protected List<DoutorDTO> toDTOsDoctors(List<Doutor> docs) {
        return docs.stream().map(this::toDTOcomRegistosDoutor).collect(Collectors.toList());
    }
    protected List<AdministradorDTO> toDTOsAdmins(List<Administrador> admins) {
        return admins.stream().map(this::toDTOcomRegistosAdministrador).collect(Collectors.toList());
    }
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/alladmins/")
    @RolesAllowed({"Administrador","Doutor"})
    public List<AdministradorDTO> getAllAdmins() {
        return toDTOsAdmins(adminBean.getAdmins());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/alldocs/")
    @RolesAllowed({"Administrador","Doutor"})
    public List<DoutorDTO> getAllDocs() {
        return toDTOsDoctors(doutorBean.getDoctors());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/allusers/")
    @RolesAllowed({"Administrador","Doutor"})
    public List<UtilizadorDTO> getAllUsers() {
        return toDTOsUtilizadores(utilizadorNormalBean.getNormalUsers());
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/utentesDoc/{username}")
    public List<UtilizadorDTO> getAllUsersByDoctor(@PathParam("username") String username) {

        List<UtilizadorNormal> utentesAll = utilizadorNormalBean.getNormalUsers();
        List<UtilizadorNormal> byDoc = new LinkedList<>();
        for (UtilizadorNormal s:utentesAll) {
            if(s.getDoctor().getUserName()==username){
                byDoc.add(s);
            }
        }
        return toDTOsUtilizadores(byDoc);
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


        return list;
    }




    @GET
    @Path("{username}/{type}")
    public Response getUserDetails(@PathParam("username") String username,@PathParam("type") String type)
            throws MyEntityNotFoundException {

        //check 
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrador") ||  securityContext.isUserInRole("Doutor")||
        securityContext.isUserInRole("UtilizadorNormal")  &&
                        principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        switch (type){
            case "utente":
                UtilizadorNormal student = utilizadorNormalBean.getUserByUsername(username);
                if(student == null) {
                    throw new MyEntityNotFoundException("User with username " +
                            username + " not found.");
                }
                return Response.status(Response.Status.OK)
                        .entity(toDTOsemRegistos(student))
                        .build();

            case "doutor":
                Doutor doutor = doutorBean.getUserByUsername(username);
                if(doutor == null) {
                    throw new MyEntityNotFoundException("User with username " +
                            username + " not found.");
                }
                return Response.status(Response.Status.OK)
                        .entity(toDTOcomRegistosDoutor(doutor))
                        .build();

            case "admin":
                Administrador admin = adminBean.getUserByUsername(username);
                if(admin == null) {
                    throw new MyEntityNotFoundException("User with username " +
                            username + " not found.");
                }
                return Response.status(Response.Status.OK)
                        .entity(toDTOcomRegistosAdministrador(admin))
                        .build();
        }
        throw new MyEntityNotFoundException("User with username " +
                username + " not found.");
    }

    @GET
    @Path("{username}/registers")
    public Response getStudentDetailsWhitRegisters(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        //check

        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrador") || securityContext.isUserInRole("Doutor") ||
                securityContext.isUserInRole("UtilizadorNormal")  &&
                        principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(securityContext.isUserInRole("Administrador")){
            Administrador doutor = adminBean.getUserByUsername(username);
            return Response.status(Response.Status.OK)
                    .entity(toDTOcomRegistosAdministrador(doutor))
                    .build();
        }
        if(securityContext.isUserInRole("Doutor")){
            Doutor doutor = doutorBean.getUserByUsername(username);
            return Response.status(Response.Status.OK)
                    .entity(toDTOcomRegistosDoutor(doutor))
                    .build();
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

    @POST
    @Path("/new/create")

    public Response createUser (UtilizadorDTO utilizadorDTO) throws MyEntityNotFoundException{

        utilizadorNormalBean.create(utilizadorDTO.getUsername(),utilizadorDTO.getPassword(),utilizadorDTO.getEmail(),utilizadorDTO.getDoutorId());
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/new/doctor/create")
    @RolesAllowed({"Administrador"})
    public Response createDoctor (DoutorDTO doutorDTO) throws MyEntityNotFoundException{

        doutorBean.create(doutorDTO.getPassword(),doutorDTO.getEmail(),doutorDTO.getUsername());
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/new/admin/create")
    @RolesAllowed({"Administrador"})
    public Response createAdmin (AdministradorDTO administradorDTO) throws MyEntityNotFoundException{

        adminBean.create(administradorDTO.getPassword(),administradorDTO.getEmail(),administradorDTO.getUsername());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/user/{idColestrol}")
    @RolesAllowed({"Administrador"})
    public Response deleteUser (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        utilizadorNormalBean.delete(idColestrol);

        return Response.status(Response.Status.ACCEPTED).build();
    }
    @DELETE
    @Path("/doctor/{idColestrol}")
    @RolesAllowed({"Administrador"})
    public Response deleteDoctor (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        doutorBean.delete(idColestrol);

        return Response.status(Response.Status.ACCEPTED).build();
    }
    @DELETE
    @Path("/admin/{idColestrol}")
    @RolesAllowed({"Administrador"})
    public Response deleteAdmin (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        adminBean.delete(idColestrol);

        return Response.status(Response.Status.ACCEPTED).build();
    }

}
