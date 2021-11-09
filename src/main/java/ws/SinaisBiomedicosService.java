package ws;

import dtos.SinalBiomedicoDTO;
import ejbs.ColestrolBean;
import ejbs.PesagemBean;
import ejbs.UtilizadorNormalBean;
import entities.Colestrol;
import entities.Pesagem;
import entities.SinalBiomedico;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Path("/biosinais")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SinaisBiomedicosService {

    @EJB
    private ColestrolBean colestrolBean;
    @EJB
    private UtilizadorNormalBean utilizadorBean;
    @EJB
    private PesagemBean pesagemBean;


    /// Colestrol

    private SinalBiomedicoDTO toDTO(Colestrol colestrol) {

        List<Float> helper = new LinkedList<>();
        helper.add(colestrol.getNivelColestrol());
        return new SinalBiomedicoDTO(
                colestrol.getId(),
                colestrol.getDate()+"",
                "Colestrol",
                helper,
                0,
                300,
                colestrol.getUtilizadorNormal().getId()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<SinalBiomedicoDTO> toDTOsColestrol(List<Colestrol> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/") // means: the relative url path is “/api/students/”
    public List<SinalBiomedicoDTO> getAllColestrolRegisters() {

        return toDTOsColestrol(colestrolBean.getAllColestrol());
    }

    @GET
    @Path("/colestrol/{id}")
    public Response getColestrolByUser(@PathParam("id") String idUtilizador){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsColestrol(utilizadorNormal.getColestrolList()))
                    .build();

        }
        Colestrol colestrol = colestrolBean.find(idUtilizador);
        if (colestrol != null){
            return Response.status(Response.Status.OK)
                    .entity(toDTO(colestrol))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();
    }


    @POST
    @Path("/colest/create")
    public Response createColestrol () throws MyEntityNotFoundException{
        //colestrolBean.create(sinalBiomedicoDTO.getValue().get(0),sinalBiomedicoDTO.getUtilizadorNormalID());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/colestrol/{idColestrol}")
    public Response updateColestrol (@PathParam("idColestrol") String idColestrol, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        colestrolBean.update(idColestrol, sinalBiomedicoDTO );

        return Response.status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/colestrol/{idColestrol}")
    public Response deleteColestrol (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        colestrolBean.delete(idColestrol);

        return Response.status(Response.Status.GONE).build();
    }

    /// Pesagem

    private SinalBiomedicoDTO toDTO(Pesagem pesagem) {

        List<Float> helper = new LinkedList<>();
        helper.add(pesagem.getAltura());
        helper.add(pesagem.getPeso());
        return new SinalBiomedicoDTO(
                pesagem.getId(),
                pesagem.getDate()+"",
                "Pesagem",
                helper,
                0,
                300,
                pesagem.getUtilizadorNormal().getId()
        );

    }

    // converts an entire list of entities into a list of DTOs
    private List<SinalBiomedicoDTO> toDTOsPesagens(List<Pesagem> pesagems) {
        return pesagems.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/pesagem/") // means: the relative url path is “/api/students/”
    public List<SinalBiomedicoDTO> getAllPesagensRegisters() {

        return toDTOsPesagens(pesagemBean.getAllPesagens());
    }

    @GET
    @Path("/pesagem/{id}")
    public Response getPesagemByUser(@PathParam("id") String id){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(id);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsPesagens(utilizadorNormal.getPesagemList()))
                    .build();

        }
        Pesagem pesagem = pesagemBean.find(id);
        if (pesagem != null){
            return Response.status(Response.Status.OK)
                    .entity(toDTO(pesagem))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();
    }

    @PUT
    @Path("/pesagem/{idPesagem}")
    public Response updatePesagem (@PathParam("idPesagem") String idPesagem, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        pesagemBean.update(idPesagem, sinalBiomedicoDTO );

        return Response.status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/pesagem/{idPesagem}")
    public Response deletePesagem (@PathParam("idPesagem") String idPesagem) throws MyEntityNotFoundException {
        pesagemBean.delete(idPesagem);

        return Response.status(Response.Status.GONE).build();
    }

}
