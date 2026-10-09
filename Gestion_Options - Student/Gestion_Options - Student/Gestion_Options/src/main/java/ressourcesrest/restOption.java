package ressourcesrest;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import entities.Option;
import metiers.OptionBusiness;
import javax.ws.rs.PUT;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {

    public static OptionBusiness optB= new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String d){

        List <Option>l= optB.getListeOptions();
        if(d==null){
        l=optB.getListeOptions();
        }
        else{
        l=optB.getOptionsByDomaine(d);
        }
    if(l.isEmpty()){
        return Response.status(Response.Status.NO_CONTENT).entity(l).build();
    }
        return Response.status(200).entity(l).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)

    public Response ajoutOption(Option op){
        if(optB.addOption(op)){
            return Response.status(200).build();
        }
        return Response.status(400).build();

    }
    @Path("{id}")
    @DELETE
    public Response deleteOption(@PathParam("id") int id) {
        if (optB.deleteOption(id)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOpt(@PathParam("id") int id, Option op){
        op.setCodeOption(id);
        if(optB.updateOption(id,op)){
            return Response.status(200).build();
        }
        return Response.status(400).build();


    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionById(@PathParam("id") int id) {
        Option op = optB.getOptionByCode(id);          // cherche l'option
        if (op != null) {
            return Response.status(200).entity(op).build();   // trouvée → 200 + l'option
        }
        return Response.status(404).build();                  // introuvable → 404
    }



}
