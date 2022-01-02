package ws;

import dtos.GraphDTO;
import dtos.PrescricaoDTO;
import dtos.SinalBiomedicoDTO;
import ejbs.DoutorBean;
import ejbs.PrescricaoBean;
import ejbs.UtilizadorNormalBean;
import entities.Colestrol;
import entities.Doutor;
import entities.Prescricao;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.management.relation.Role;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Path("/prescricoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrescricoesService {

    @EJB
    protected PrescricaoBean prescricaoBean;
    @EJB
    protected UtilizadorNormalBean utilizadorBean;
    @EJB
    protected DoutorBean doutorBean;



    protected PrescricaoDTO toDTO(Prescricao prescricao) {
        return new PrescricaoDTO(
                prescricao.getId(),
                prescricao.getDoutor().getUserName(),
                prescricao.getDoutor().getId(),
                prescricao.getUtilizadorNormal().getId(),
                prescricao.getDataFim(),
                prescricao.getDataInicio(),
                prescricao.getDescricao(),
                prescricao.getTipo()

        );
    }

    // converts an entire list of entities into a list of DTOs
    protected List<PrescricaoDTO> toDTOsPrescricoes(List<Prescricao> prescricaos) {
        return prescricaos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    @RolesAllowed({"Administrador","Doutor"})
    public List<PrescricaoDTO> getAllPrescricoesRegisters() {

        return toDTOsPrescricoes(prescricaoBean.getAllPrescricoes());
    }
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/{id}/graph")
    @RolesAllowed({"Administrador","UtilizadorNormal","Doutor"})
    public Response getDataForGraph(@PathParam("id") String idUtilizador) {
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            List<Float> data = new LinkedList<>();
            List<String> label = new LinkedList<>();


            for (Colestrol colestrol:utilizadorNormal.getColestrolList()
            ) {
                data.add(colestrol.getNivelColestrol());
                label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(colestrol.getDate()));
            }

            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADOR_RECORD")
                .build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"UtilizadorNormal","Administrador","Doutor"})
    public Response getPrescricaoByUser(@PathParam("id") String idUtilizador){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);


        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsPrescricoes(utilizadorNormal.getPrescricoesList()))
                    .build();

        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();

    }
    @GET
    @Path("/doutor/{id}")
    @RolesAllowed({"Administrador","Doutor"})
    public Response getPrescricaoByDoctor(@PathParam("id") String idUtilizador){
        Doutor utilizadorNormal = doutorBean.find(idUtilizador);
        List<Prescricao> pes = prescricaoBean.getUserByUsername(idUtilizador);
        if(pes.isEmpty()){
            return Response.status(Response.Status.OK)
                .entity("")
                .build();
        }

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsPrescricoes(pes))
                    .build();

        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_DOCTOR_RECORD")
                .build();

    }


    @POST
    @Path("/{id}/create")
    @RolesAllowed({"Administrador","Doutor"})
    public Response createPrescricao (@PathParam("id") String idUtilizador,  PrescricaoDTO prescricaoDTO) throws MyEntityNotFoundException{

        prescricaoBean.create(new Date(prescricaoDTO.getDataFim()),prescricaoDTO.getTipo(),prescricaoDTO.getDescricao(), prescricaoDTO.getUtilizadorNormalId(),prescricaoDTO.getDoutorId());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrador","Doutor"})
    public Response updatePrescricao (@PathParam("id") String idColestrol, PrescricaoDTO prescricaoDTO) throws MyEntityNotFoundException {

        prescricaoBean.update(idColestrol, prescricaoDTO);
        return Response.status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrador","Doutor"})
    public Response deleteColestrol (@PathParam("id") String idColestrol) throws MyEntityNotFoundException {
        prescricaoBean.delete(idColestrol);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
