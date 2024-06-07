package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.services.IClinicaService;
import equipo1obrasocial.util.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/clinica")
@RequestScoped
@Api(tags = "Clinica Controller", description = "Operaciones relacionadas con la gestión de clínicas")
public class ClinicaController {

	@Inject
	private IClinicaService clinicaService;
	
	@POST
	@Path("/crearClinica/{nombre}/{direccion}")
    @ApiOperation(value = "Crear una nueva clínica", notes = "Crea una nueva clínica con el nombre y dirección especificados")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Clínica creada exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear la clínica")
    })
	public ResponseEntity<Object> altaClinica(@PathParam("nombre") String nombre, @PathParam("direccion") String direccion) {
	    try {
	        clinicaService.crearClinica(nombre,direccion);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Clínica creada exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
}
