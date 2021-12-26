package ws;

import com.nimbusds.jose.proc.SecurityContext;
import dtos.SinalBiomedicoDTO;
import ejbs.*;
import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Path("/biosinais")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SinaisBiomedicosService {

    @Context
    protected SecurityContext securityContext;
    @EJB
    protected ColestrolBean colestrolBean;
    @EJB
    protected UtilizadorNormalBean utilizadorBean;
    @EJB
    protected PesagemBean pesagemBean;
    @EJB
    protected BPMBean bpmBean;
    @EJB
    protected OutroBean outroBean;


    /// Colestrol

    protected SinalBiomedicoDTO toDTO(Colestrol colestrol) {

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
    protected List<SinalBiomedicoDTO> toDTOsColestrol(List<Colestrol> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/")
    @RolesAllowed({"UtilizadorNormal"})
    public List<SinalBiomedicoDTO> getAllColestrolRegisters() {

        return toDTOsColestrol(colestrolBean.getAllColestrol());
    }

    @GET
    @Path("/colestrol/{id}")
    @RolesAllowed({"Administrador"})
    public Response getColestrolByUser(@PathParam("id") String idUtilizador){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsColestrol(utilizadorNormal.getColestrolList()))
                    .build();

        }
        Colestrol colestrol = colestrolBean.find(idUtilizador);
        System.out.println(colestrol.toString());
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
    @Path("/colestrol/{idUtilizador}/create")
    public Response createColestrol (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        colestrolBean.create(sinalBiomedicoDTO.getValue().get(0),idUtilizador);
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

    protected SinalBiomedicoDTO toDTO(Pesagem pesagem) {

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
    protected List<SinalBiomedicoDTO> toDTOsPesagem(List<Pesagem> pesagems) {
        return pesagems.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/pesagem/")
    public List<SinalBiomedicoDTO> getAllPesagensRegisters() {

        return toDTOsPesagem(pesagemBean.getAllPesagens());
    }

    @GET
    @Path("/pesagem/{id}")
    public Response getPesagemByUser(@PathParam("id") String id){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(id);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsPesagem(utilizadorNormal.getPesagemList()))
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

    @POST
    @Path("/pesagem/{idUtilizador}/create")
    public Response createPesagem (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        if (sinalBiomedicoDTO.getValue().size()!=2){
            throw new IndexOutOfBoundsException("Não foi enviado array com dois elementos "+sinalBiomedicoDTO.getValue().size()+sinalBiomedicoDTO.getValue().get(0));
        }
        pesagemBean.create(sinalBiomedicoDTO.getValue().get(0),sinalBiomedicoDTO.getValue().get(1),idUtilizador);
        return Response.status(Response.Status.CREATED).build();
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

    //BPM

    protected SinalBiomedicoDTO toDTO(BPM bpm) {

        List<Float> helper = new LinkedList<>();
        helper.add((float) bpm.getNumeroBatimentos());

        return new SinalBiomedicoDTO(
                bpm.getId(),
                bpm.getDate()+"",
                "Bpm",
                helper,
                0,
                300,
                bpm.getUtilizadorNormal().getId()
        );

    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoDTO> toDTOsBPM(List<BPM> bpms) {
        return bpms.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/bpm/")
    public List<SinalBiomedicoDTO> getAllBPMSRegisters() {

        return toDTOsBPM(bpmBean.getAllBPM());
    }

    @GET
    @Path("/bpm/{id}")
    public Response getBpmByUser(@PathParam("id") String id){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(id);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsBPM(utilizadorNormal.getBpmList()))
                    .build();

        }
        BPM bpm = bpmBean.find(id);
        if (bpm != null){
            return Response.status(Response.Status.OK)
                    .entity(toDTO(bpm))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();
    }

    @POST
    @Path("/bpm/{idUtilizador}/create")
    public Response createBpm (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        bpmBean.create(sinalBiomedicoDTO.getValue().get(0),idUtilizador);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/bpm/{idBpm}")
    public Response updateBpm (@PathParam("idBpm") String idBpm, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        bpmBean.update(idBpm, sinalBiomedicoDTO );

        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @Path("/bpm/{idBpm}")
    public Response deleteBpm (@PathParam("idBpm") String idPesagem) throws MyEntityNotFoundException {
        bpmBean.delete(idPesagem);

        return Response.status(Response.Status.GONE).build();
    }


    //BPM

    protected SinalBiomedicoDTO toDTO(Outro outro) {

        List<Float> helper = new LinkedList<>();
        helper.add((float) outro.getValue());

        return new SinalBiomedicoDTO(
                outro.getId(),
                outro.getDate()+"",
                outro.getName()
                ,
                helper,
                outro.getMinValue(),
                outro.getMaxValue(),
                outro.getUtilizadorNormal().getId()
        );

    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoDTO> toDTOsOutro(List<Outro> outros) {
        return outros.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/outro/")
    public List<SinalBiomedicoDTO> getAllOutroRegisters() {

        return toDTOsOutro(outroBean.getAllOutros());
    }

    @GET
    @Path("/outro/{id}")
    public Response getOutroByUser(@PathParam("id") String id){
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(id);

        if (utilizadorNormal != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTOsOutro(utilizadorNormal.getOutrosList()))
                    .build();

        }
        Outro outro = outroBean.find(id);
        if (outro != null){
            return Response.status(Response.Status.OK)
                    .entity(toDTO(outro))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();
    }

    @POST
    @Path("/bpm/{idOutro}/create")
    public Response createOutro (@PathParam("idOutro") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        outroBean.create(sinalBiomedicoDTO,idUtilizador);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/bpm/{idOutro}")
    public Response updateOutro (@PathParam("idOutro") String idBpm, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        outroBean.update(idBpm, sinalBiomedicoDTO );

        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @Path("/bpm/{idOutro}")
    public Response deleteOutro (@PathParam("idOutro") String idPesagem) throws MyEntityNotFoundException {
        bpmBean.delete(idPesagem);

        return Response.status(Response.Status.GONE).build();
    }
}
