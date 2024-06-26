package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.dtos.request.RecetaDTORequest;
import equipo1obrasocial.dtos.response.RecetaDTOResponse;
import equipo1obrasocial.services.implementations.RecetaService;
import equipo1obrasocial.util.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/recetas")
@RequestScoped
@Api(tags = "Receta Controller", description = "Operaciones relacionadas con la gestión de recetas")
public class RecetaController {

	@Inject
	private RecetaService recetaService;
	
	@POST
	@Path("/crearReceta")
    @ApiOperation(value = "Crear una nueva receta", notes = "Crea una nueva receta con la información especificada en el DTO")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Receta creada exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear la receta")
    })
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
    @ApiOperation(value = "Obtener receta por turno", notes = "Obtiene la receta asociada a un turno específico mediante el ID del turno")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Receta obtenida exitosamente"),
        @ApiResponse(code = 400, message = "Error al obtener la receta")
    })
    public ResponseEntity<Object> traerRecetaPorTurno(@PathParam("id") long id) {
        try {
            RecetaDTOResponse dto = recetaService.traerRecetaPorTurno(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("/traerRecetaId/{id}")
    @ApiOperation(value = "Obtener receta por id", notes = "Obtiene la receta por Id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Receta obtenida exitosamente"),
        @ApiResponse(code = 400, message = "Error al obtener la receta")
    })
    public Response traerRecetaPorId(@PathParam("id") long id) {

            RecetaDTOResponse dto = recetaService.traerRecetaPorIDReceta(id);
            return Response.ok(dto).build();
    }
}
