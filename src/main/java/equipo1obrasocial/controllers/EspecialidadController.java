package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import equipo1obrasocial.services.IEspecialidadService;
import equipo1obrasocial.util.Mensaje;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/especialidad")
@RequestScoped
public class EspecialidadController {
	
	@Inject
	private IEspecialidadService especialidadService;

	@POST
	@Path("/crearEspecialidad/{nombre}")
	public ResponseEntity<Object> altaEspecialidad(@PathParam("nombre") String nombre) {
	    try {
	        especialidadService.crearEspecialidad(nombre);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Especialidad creada exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}


}
