package ws;

import com.nimbusds.jose.proc.SecurityContext;
import dtos.GraphDTO;
import dtos.SinalBiomedicoDTO;
import dtos.SinalBiomedicoOutroDTO;
import ejbs.*;
import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
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
        List<String> helper = new LinkedList<>();
        helper.add(colestrol.getNivelColestrol()+"");

        return new SinalBiomedicoDTO(
                colestrol.getId(),
                colestrol.getDate(), colestrol.getUtilizadorNormal().getUserName(),
                helper,
                colestrol.getUtilizadorNormal().getId(),
                colestrol.getDescricao(),
                colestrol.getClassification()
        );
    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoDTO> toDTOsColestrol(List<Colestrol> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/")
    @RolesAllowed({"Doutor"})
    public List<SinalBiomedicoDTO> getColestrolRegisters() {
        return toDTOsColestrol(colestrolBean.getColestrol());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/")
    @RolesAllowed({"Administrador"})
    public List<SinalBiomedicoDTO> getAllColestrolRegisters() {
        return toDTOsColestrol(colestrolBean.getColestrol());
    }
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/{id}/graph")
    @RolesAllowed({"Administrador","UtilizadorNormal"})
    public Response getDataForGraph(@PathParam("id") String idUtilizador) {
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            List<Float> data = new LinkedList<>();
            List<String> label = new LinkedList<>();


            for (Colestrol colestrol:utilizadorNormal.getColestrolList()
            ) {
                if (colestrol.isDeleted()){
                    data.add(colestrol.getNivelColestrol());
                    label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(colestrol.getDate()));

                }
          }

            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADOR_RECORD")
                .build();
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/colestrol/graph")
    @RolesAllowed({"Administrador","Doutor"})
    public Response getDataForGraph() {
        List<UtilizadorNormal> all = utilizadorBean.getAllNormalUsers();
        System.out.println("Are you Here ?");
        List<Float> data = new LinkedList<>();
        List<String> label = new LinkedList<>();
        for (UtilizadorNormal u: all
             ) {
            if (u != null) {
                for (Colestrol colestrol:u.getColestrolList()
                ) {
                    if (!colestrol.isDeleted()){
                        data.add(colestrol.getNivelColestrol());
                        label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(colestrol.getDate()));

                    }
                      }
            }
        }
        if (all != null){
            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADORES_RECORD")
                .build();


    }
    @GET
    @Path("/colestrol/{id}")
    @RolesAllowed({"UtilizadorNormal","Administrador","Doutor"})
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
    @Path("/colestrol/{idUtilizador}/create")
    public Response createColestrol (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{

        colestrolBean.create(Float.parseFloat(sinalBiomedicoDTO.getValue().get(1)),idUtilizador, sinalBiomedicoDTO.getDescricao());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/colestrol/{idColestrol}")
    public Response updateColestrol (@PathParam("idColestrol") String idColestrol, SinalBiomedicoDTO sinalBiomedicoDTOmv) throws MyEntityNotFoundException {

        colestrolBean.update(idColestrol, sinalBiomedicoDTOmv);

        return Response.status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/colestrol/{idColestrol}")
    public Response deleteColestrol (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        colestrolBean.delete(idColestrol);

        return Response.status(Response.Status.ACCEPTED).build();
    }

    /// Pesagem

    protected SinalBiomedicoDTO toDTO(Pesagem pesagem) {

        List<String> helper = new LinkedList<>();
        helper.add(pesagem.getAltura().toString());
        helper.add(pesagem.getPeso().toString());
        helper.add(String. format("%.2f", pesagem.getIMC()));
        return new SinalBiomedicoDTO(
                pesagem.getId(),
                pesagem.getDate(),
                pesagem.getUtilizadorNormal().getUserName(),
                helper,
                pesagem.getUtilizadorNormal().getId(),
                pesagem.getDescricao(),
                pesagem.getClassification()
        );

    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/pesagem/{id}/graph")
    @RolesAllowed({"Administrador","UtilizadorNormal"})
    public Response getDataForGraphPesagem(@PathParam("id") String idUtilizador) {
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            List<Float> data = new LinkedList<>();
            List<String> label = new LinkedList<>();


            for (Pesagem pesagem:utilizadorNormal.getPesagemList()
            ) {
                if (!pesagem.isDeleted()){
                    data.add(pesagem.getIMC());
                    label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(pesagem.getDate()));

                }
        }

            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADOR_RECORD")
                .build();
    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoDTO> toDTOsPesagem(List<Pesagem> pesagems) {
        return pesagems.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/pesagem/")
    public List<SinalBiomedicoDTO> getAllPesagensRegisters() {

        return toDTOsPesagem(pesagemBean.getPesagemRegisters());
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


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/pesagem/graph")
    @RolesAllowed({"Administrador","Doutor"})
    public Response getDataForGraphPesagem() {

        List<UtilizadorNormal> all2 = utilizadorBean.getAllNormalUsers();

        System.out.println("Are you Here ?");
        List<Float> data = new LinkedList<>();
        List<String> label = new LinkedList<>();
        for (UtilizadorNormal u: all2
        ) {
            if (u != null) {
                for (Pesagem pesagem:u.getPesagemList()
                ) {
                    if (!pesagem.isDeleted()){
                        data.add(pesagem.getIMC());
                        label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(pesagem.getDate()));
                    }

               }
            }
        }
        if (all2 != null){
            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADORES_RECORD")
                .build();
    }


    @POST
    @Path("/pesagem/{idUtilizador}/create")
    public Response createPesagem (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        if (sinalBiomedicoDTO.getValue().size()!=2){
            throw new IndexOutOfBoundsException("Não foi enviado array com dois elementos "+ sinalBiomedicoDTO.getValue().size()+ sinalBiomedicoDTO.getValue().get(0));
        }
        pesagemBean.create(Float.parseFloat(sinalBiomedicoDTO.getValue().get(0).replace(',','.')),Float.parseFloat(sinalBiomedicoDTO.getValue().get(1).replace(',','.')) ,idUtilizador,sinalBiomedicoDTO.getDescricao());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/pesagem/{idPesagem}")
    public Response updatePesagem (@PathParam("idPesagem") String idPesagem, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        pesagemBean.update(idPesagem, sinalBiomedicoDTO);

        return Response.status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/pesagem/{idPesagem}")
    public Response deletePesagem (@PathParam("idPesagem") String idPesagem) throws MyEntityNotFoundException {
        pesagemBean.delete(idPesagem);

        return Response.status(Response.Status.ACCEPTED).build();
    }

    //BPM

    protected SinalBiomedicoDTO toDTO(BPM bpm) {

        List<String> helper = new LinkedList<>();
        helper.add( bpm.getNumeroBatimentos() +"");

        return new SinalBiomedicoDTO(
                bpm.getId(),
                bpm.getDate(),
                bpm.getUtilizadorNormal().getUserName(),
                helper,
                bpm.getUtilizadorNormal().getId(),
                bpm.getDescricao(),
                bpm.getClassification()
        );

    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoDTO> toDTOsBPM(List<BPM> bpms) {
        return bpms.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/bpm/")
    public List<SinalBiomedicoDTO> getAllBPMSRegisters() {
        return toDTOsBPM(bpmBean.getBpmRegisters());
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
    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/bpms/graph")
    @RolesAllowed({"Administrador","Doutor"})
    public Response getDataForGraphBPM() {

        List<UtilizadorNormal> all3 = utilizadorBean.getAllNormalUsers();

        List<Float> data = new LinkedList<>();
        List<String> label = new LinkedList<>();
        for (UtilizadorNormal u: all3
        ) {
            if (u != null) {
                for (BPM pesagem:u.getBpmList()
                ) {
                    if (!pesagem.isDeleted()){
                    data.add((float) pesagem.getNumeroBatimentos());
                    label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(pesagem.getDate()));

                    }
                }

            }
        }
        if (all3 != null){
            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADORES_RECORD")
                .build();
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/bpm/{id}/graph")
    @RolesAllowed({"Administrador","UtilizadorNormal"})
    public Response getDataForGraphBPM(@PathParam("id") String idUtilizador) {
        UtilizadorNormal utilizadorNormal = utilizadorBean.find(idUtilizador);

        if (utilizadorNormal != null) {
            List<Float> data = new LinkedList<>();
            List<String> label = new LinkedList<>();


            for (BPM pesagem:utilizadorNormal.getBpmList()
            ) {
                if (!pesagem.isDeleted()) {
                    data.add((float) pesagem.getNumeroBatimentos());
                    label.add(new SimpleDateFormat("kk:mm dd/MM/yyyy").format(pesagem.getDate()));

                }}

            return Response.status(Response.Status.OK)
                    .entity(new GraphDTO(data,label))
                    .build();

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_UTILIZADOR_RECORD")
                .build();
    }

    @POST
    @Path("/bpm/{idUtilizador}/create")
    public Response createBpm (@PathParam("idUtilizador") String idUtilizador,  SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        bpmBean.create(Float.parseFloat(sinalBiomedicoDTO.getValue().get(0)),sinalBiomedicoDTO.getDescricao(),idUtilizador);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/bpm/{idBpm}")
    public Response updateBpm (@PathParam("idBpm") String idBpm, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        bpmBean.update(idBpm, sinalBiomedicoDTO);

        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @Path("/bpm/{idBpm}")
    public Response deleteBpm (@PathParam("idBpm") String idPesagem) throws MyEntityNotFoundException {
        bpmBean.delete(idPesagem);

        return Response.status(Response.Status.ACCEPTED).build();
    }


    //BPM

    protected SinalBiomedicoOutroDTO toDTO(Outro outro) {

        List<String> helper = new LinkedList<>();
        helper.add( outro.getValue()+"");

        return new SinalBiomedicoOutroDTO(
                outro.getId(),
                outro.getDate(),
                outro.getName(),
                outro.getValue()+"",
                outro.getUtilizadorNormal().getUserName(),
                outro.getOutroCategories().getId(),
                outro.getDescricao()
        );

    }

    // converts an entire list of entities into a list of DTOs
    protected List<SinalBiomedicoOutroDTO> toDTOsOutro(List<Outro> outros) {
        return outros.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/outro/")
    @RolesAllowed({"Administrador","Doutor"})
    public List<SinalBiomedicoOutroDTO> getAllOutroRegisters() {

        return toDTOsOutro(outroBean.getBpmRegisters());
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
    @Path("/outro/{idOutro}/create")
    public Response createOutro (@PathParam("idOutro") String idUtilizador,  SinalBiomedicoOutroDTO sinalBiomedicoDTO) throws MyEntityNotFoundException{
        outroBean.create(Float.parseFloat(sinalBiomedicoDTO.getValue()),sinalBiomedicoDTO.getDescricao(),idUtilizador,sinalBiomedicoDTO.getOutroCategoriesID());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/outro/{idOutro}")
    public Response updateOutro (@PathParam("idOutro") String idBpm, SinalBiomedicoDTO sinalBiomedicoDTO) throws MyEntityNotFoundException {

        outroBean.update(idBpm, sinalBiomedicoDTO);

        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @Path("/outro/{idOutro}")
    public Response deleteOutro (@PathParam("idOutro") String idPesagem) throws MyEntityNotFoundException {
        outroBean.delete(idPesagem);

        return Response.status(Response.Status.ACCEPTED).build();
    }




}
