package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1obrasocial.services.ITurnoService;
import equipo1obrasocial.util.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/turnos")
@RequestScoped
@Api(tags = "Turno Controller", description = "Operaciones relacionadas con la gestión de Turnos")
public class TurnoController {
	
	@Inject
	private ITurnoService turnoService;
	
	@POST
	@Path("/crearTurno")
    @ApiOperation(value = "Crear un nuevo turno", notes = "Crea un nuevo turno para un médico y paciente")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Turno creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el turno")
        })
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
    @ApiOperation(value = "Actualizar un turno existente", notes = "Actualiza la información de un turno existente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Turno actualizado exitosamente"),
        @ApiResponse(code = 400, message = "Error al actualizar el turno")
    })
    public ResponseEntity<Object> actualizarTurno(@RequestBody TurnoActualizarDTORequest dto){
        try{
        	turnoService.actualizarTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Su turno se actualizo exitosamente"));
        }catch(Exception e){
            return new ResponseEntity<>(new Mensaje(e.getMessage()) , HttpStatus.BAD_REQUEST);
        }
    }

	@DELETE
	@Path("/eliminarTurno")
    @ApiOperation(value = "Eliminar un turno", notes = "Elimina un turno existente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Turno eliminado exitosamente"),
        @ApiResponse(code = 400, message = "Error al eliminar el turno")
    })
    public ResponseEntity<Object> eliminarTurno(@RequestBody TurnoEliminarDTORequest dto){
        try{
        	turnoService.eliminarTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Se eliminó el turno exitosamente"));

        }catch(Exception e){
            return new ResponseEntity<>(new Mensaje(e.getMessage()) , HttpStatus.BAD_REQUEST);
        }
    }

}
