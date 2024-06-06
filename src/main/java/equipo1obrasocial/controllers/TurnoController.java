package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.services.ITurnoService;
import equipo1obrasocial.util.Mensaje;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/turnos")
@RequestScoped
public class TurnoController {
	
	@Inject
	private ITurnoService turnoService;
	
	@POST
	@Path("/crearTurno")
    public ResponseEntity<Object> altaTurno(@RequestBody TurnoDTOMedicoPaciente dto){
        try{
        	turnoService.crearTurno(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Se agrego el turno exitosamente para el dia "+dto.getFecha_hora()));
        }catch(Exception e){
            return new ResponseEntity<>(new Mensaje(e.getMessage()) , HttpStatus.BAD_REQUEST);
        }
    }
	
	@PUT
	@Path("/actualizarTurno")
    public ResponseEntity<Object> actualizarTurno(@RequestBody TurnoActualizarDTORequest dto){
        try{
        	turnoService.actualizarTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Su turno se actualizo exitosamente"));
        }catch(Exception e){
            return new ResponseEntity<>(new Mensaje(e.getMessage()) , HttpStatus.BAD_REQUEST);
        }
    }

}
