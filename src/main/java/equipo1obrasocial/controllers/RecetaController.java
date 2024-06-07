package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.dtos.request.RecetaDTORequest;
import equipo1obrasocial.dtos.response.RecetaDTOResponse;
import equipo1obrasocial.services.RecetaService;
import equipo1obrasocial.util.Mensaje;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/recetas")
@RequestScoped
public class RecetaController {

	@Inject
	private RecetaService recetaService;
	
	@POST
	@Path("/crearReceta")
	public ResponseEntity<Object> altaReceta(RecetaDTORequest dto) {
	    try {
	        recetaService.crearReceta(dto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Receta creada exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
	
    @GET
    @Path("/traerRecetaPorTurno/{id}")
    public ResponseEntity<Object> traerRecetaPorTurno(@PathParam("id") long id) {
        try {
            RecetaDTOResponse dto = recetaService.traerRecetaPorTurno(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
