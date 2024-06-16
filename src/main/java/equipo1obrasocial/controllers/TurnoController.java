package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFecha;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
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
	@Path("/crearTurnoConPaciente")
    @ApiOperation(value = "Crear un nuevo turno para un paciente determinado", notes = "Crea un nuevo turno para un médico y paciente")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Turno creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el turno")
        })
    public ResponseEntity<Object> altaTurnoConPaciente(@RequestBody TurnoDTOMedicoPaciente dto){
        	turnoService.crearTurnoConPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Se agrego el turno exitosamente para el dia "+dto.getFecha_hora()));
    }
	
	@POST
	@Path("/crearTurnoSinPaciente")
    @ApiOperation(value = "Crear un nuevo turno disponible", notes = "Crea un nuevo turno para un médico sin paciente asignado, este se encontrara libre para su asignacion luego")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Turno creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el turno")
        })
    public ResponseEntity<Object> altaTurnoSinPaciente(@RequestBody TurnoDTOMedicoFechaHora dto){

        	turnoService.crearTurnoSinPaciente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Se agrego el turno exitosamente para el dia "+dto.getFecha_hora()));
    }
	
	@POST
	@Path("/crearTurnosMedicoFechaC15Min")
    @ApiOperation(value = " ", notes = " ")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Turnos creados exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el turnos para ese dia")
        })
    public ResponseEntity<Object> altaTurnosMedicoFechaC15Min(@RequestBody TurnoDTOMedicoFecha dto){

        	turnoService.crearTurnosMedicoFechaCada15Min(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Se agregaron los turnos exitosamente para el dia "+dto.getFecha()));
    }
	
	@POST
	@Path("/crearTurnosMedicoFechaC20Min")
    @ApiOperation(value = " ", notes = " ")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Turnos creados exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el turnos para ese dia")
        })
    public ResponseEntity<Object> altaTurnosMedicoFechaC20Min(@RequestBody TurnoDTOMedicoFecha dto){

        	turnoService.crearTurnosMedicoFechaCada20Min(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Se agregaron los turnos exitosamente para el dia "+dto.getFecha()));
    }
	
	@DELETE
	@Path("/darBajaTurno")
	@ApiOperation(value = "Dar de baja un turno", notes = "Libera un turno existente para permitir su reasignación")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Turno dado de baja exitosamente"),
        @ApiResponse(code = 400, message = "Error al dar de baja el turno")
    })
    public ResponseEntity<Object> darBajaTurno(@RequestBody TurnoEliminarDTORequest dto){

        	turnoService.darBajaTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Se liberó el turno exitosamente"));

    } 
	
	@PUT
	@Path("/actualizarTurno")
    @ApiOperation(value = "Actualizar un turno existente", notes = "Actualiza la información de un turno existente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Turno actualizado exitosamente"),
        @ApiResponse(code = 400, message = "Error al actualizar el turno")
    })
    public ResponseEntity<Object> actualizarTurno(@RequestBody TurnoActualizarDTORequest dto){

        	turnoService.actualizarTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Su turno se actualizo exitosamente"));

    }

	@DELETE
	@Path("/eliminarTurno")
    @ApiOperation(value = "Eliminar un turno", notes = "Elimina un turno existente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Turno eliminado exitosamente"),
        @ApiResponse(code = 400, message = "Error al eliminar el turno")
    })
    public ResponseEntity<Object> eliminarTurno(@RequestBody TurnoEliminarDTORequest dto){

        	turnoService.eliminarTurno(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Se eliminó el turno exitosamente"));

    }

}
