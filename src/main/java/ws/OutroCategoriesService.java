package ws;

import dtos.OutroCategoriesDTO;
import dtos.SinalBiomedicoDTO;
import ejbs.OutroCategoriesBean;
import ejbs.UtilizadorNormalBean;
import entities.Colestrol;
import entities.OutroCategories;
import entities.UtilizadorNormal;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/admin/outro")
public class OutroCategoriesService {

    /// Colestrol
    @EJB
    OutroCategoriesBean outroCategoriesBean;
    @EJB
    protected UtilizadorNormalBean utilizadorBean;

    protected OutroCategoriesDTO toDTO(OutroCategories outro) {


        return new OutroCategoriesDTO(
                outro.getId(),
                outro.getDate(),
                outro.getName(),
                outro.getMaxValues(),
                outro.getMinValues(),
                new SinaisBiomedicosService().toDTOsOutro(outro.getOutro())

        );
    }

    // converts an entire list of entities into a list of DTOs
    protected List<OutroCategoriesDTO> toDTOsOutroCategories(List<OutroCategories> outros) {
        return outros.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/")
    @RolesAllowed({"Administrador"})
    public List<OutroCategoriesDTO> getAllOutroCategories() {

        return toDTOsOutroCategories(outroCategoriesBean.getAllCatagories());
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"UtilizadorNormal","Administrador","Doutor"})
    public Response getCatById(@PathParam("id") String idUtilizador){
        OutroCategories outroCategories = outroCategoriesBean.find(idUtilizador);


        if (outroCategories != null) {
            return Response.status(Response.Status.OK)
                    .entity(toDTO(outroCategories))
                    .build();

        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("ERROR_FINDING_COLESTROL_RECORD")
                .build();

    }


    @POST
    @Path("/create")
    @RolesAllowed({"Administrador"})
    public Response createOutroCategories (OutroCategoriesDTO outroCategoriesDTO) throws MyEntityNotFoundException {

        outroCategoriesBean.create(outroCategoriesDTO.getName(),outroCategoriesDTO.getMaxValues(),outroCategoriesDTO.getMinValues());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{idColestrol}")
    @RolesAllowed({"Administrador"})
    public Response updateOutroCategories (@PathParam("idColestrol") String idColestrol, OutroCategoriesDTO outroCategoriesDTO) throws MyEntityNotFoundException {

        outroCategoriesBean.update(idColestrol, outroCategoriesDTO);

        return Response.status(Response.Status.ACCEPTED)
                .build();
    }

    @DELETE
    @Path("/{idColestrol}")
    public Response deleteOutroCategories (@PathParam("idColestrol") String idColestrol) throws MyEntityNotFoundException {
        outroCategoriesBean.delete(idColestrol);

        return Response.status(Response.Status.ACCEPTED).build();
    }

}
