package ressourcesrest;
import javax.ws.rs.*;

import entities.Etudiant;
import metiers.EtudiantBusiness;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.PUT;
import entities.EtudiantList;
import entities.Option;
import javax.ws.rs.QueryParam;
@Path("etudiants")
public class restEtudiant {

    public static EtudiantBusiness etudB = new EtudiantBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> l = etudB.getAllEtudiants();
        return Response.status(200).entity(l).build();
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant e = etudB.getEtudiantByIdentifiant(id);
        if (e != null) {
            return Response.status(200).entity(e).build();   // trouvé → 200 + l'étudiant
        }
        return Response.status(404).build();                 // introuvable → 404
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e) {
        if (etudB.addEtudiant(e)) {
            return Response.status(200).entity("Etudiant ajouté").build();
        }
        return Response.status(404).entity("Option introuvable").build();
    }
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etudB.deleteEtudiant(id)) {
            return Response.status(204).build();    // supprimé → 204, sans corps
        }
        return Response.status(404).build();        // introuvable → 404
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant e) {
        e.setIdentifiant(id);                         // l'id de l'URL fait foi
        if (etudB.updateEtudiant(id, e)) {
            return Response.status(200).entity("Etudiant modifié").build();
        }
        return Response.status(404).entity("Etudiant introuvable").build();
    }
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option op = restOption.optB.getOptionByCode(codeOption);   // 1. l'option existe ?
        if (op == null) {
            return Response.status(404).build();                     //    non → 404
        }
        List<Etudiant> l = etudB.getEtudiantsByOption(op);          // 2. ses étudiants
        return Response.status(200).entity(new EtudiantList(l)).build();  // 3. emballés → XML
    }
}