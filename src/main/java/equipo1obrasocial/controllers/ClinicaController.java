package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.services.IClinicaService;
import equipo1obrasocial.util.Mensaje;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/clinica")
@RequestScoped
public class ClinicaController {

	@Inject
	private IClinicaService clinicaService;
	
	@POST
	@Path("/crearClinica/{nombre}/{direccion}")
	public ResponseEntity<Object> altaClinica(@PathParam("nombre") String nombre, @PathParam("direccion") String direccion) {
	    try {
	        clinicaService.crearClinica(nombre,direccion);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Clínica creada exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
}
