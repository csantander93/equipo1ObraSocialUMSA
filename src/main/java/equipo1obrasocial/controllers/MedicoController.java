package equipo1obrasocial.controllers;

import java.sql.Time;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.services.IMedicoService;
import equipo1obrasocial.util.Mensaje;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/medico")
@RequestScoped
public class MedicoController {

	@Inject
	private IMedicoService medicoService;
	
	@POST
	@Path("/crearMedico/{nombre}/{apellido}/{matricula}/{atencionDesde}/{atencionHasta}/{especialidad}/{idClinica}")
	public ResponseEntity<Object> altaMedico(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido, @PathParam("matricula") String matricula,
			@PathParam("atencionDesde") Time atencionDesde, @PathParam("atencionHasta") Time atencionHasta, @PathParam("especialidad") String especialidad, 
			@PathParam("idClinica") long idClinica) {
	    try {
	        medicoService.crearMedico(nombre,apellido,matricula,atencionDesde,atencionHasta,especialidad,idClinica);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Medico creado exitosamente"));
	    } catch(Exception e) {
	        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
}
