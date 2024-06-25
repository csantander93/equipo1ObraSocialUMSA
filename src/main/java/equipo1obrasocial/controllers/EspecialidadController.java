package equipo1obrasocial.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.dtos.response.EspecialidadDTOResponse;
import equipo1obrasocial.services.IEspecialidadService;
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

@Path("/especialidad")
@RequestScoped
@Api(tags = "Especialidad Controller", description = "Operaciones relacionadas con la gestión de especialidades")
public class EspecialidadController {
	
	@Inject
	private IEspecialidadService especialidadService;

	@POST
	@Path("/crearEspecialidad/{nombre}")
    @ApiOperation(value = "Crear una nueva especialidad", notes = "Crea una nueva especialidad con el nombre especificado")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Especialidad creada exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear la especialidad")
    })
	public ResponseEntity<Object> altaEspecialidad(@PathParam("nombre") String nombre) {
	    try {
	        especialidadService.crearEspecialidad(nombre);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Especialidad creada exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GET
	@Path("/traerEspecialidades")
    @ApiOperation(value = "Trae todas las especialidades disponibles", notes = "trae todas las especialidades")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Devuelve la lista de especiialidades"),
        @ApiResponse(code = 400, message = "Error al crear la especialidad")
    })
	public Response traerEspecialidades() {
		
		List<EspecialidadDTOResponse> especialidades = especialidadService.traerTodasEspecialidades();
	  
	    return Response.ok(especialidades).build();
	}


}
