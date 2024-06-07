package equipo1obrasocial.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.services.IPacienteService;
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

@Path("/paciente")
@RequestScoped
@Api(tags = "Paciente Controller", description = "Operaciones relacionadas con la gestión de pacientes")
public class PacienteController {

    @Inject
    private IPacienteService pacienteService;
    
    @POST
    @Path("/crearPaciente/{nombre}/{apellido}/{dni}/{num_afiliado}/{fecha_nac}")
    @ApiOperation(value = "Crear un nuevo paciente", notes = "Crea un nuevo paciente con la información especificada")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Paciente creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el paciente")
    })
    public ResponseEntity<Object> altaPaciente(
            @PathParam("nombre") String nombre,
            @PathParam("apellido") String apellido,
            @PathParam("dni") String dni,
            @PathParam("num_afiliado") String num_afiliado,
            @PathParam("fecha_nac") String fecha_nacStr) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate fecha_nac = LocalDate.parse(fecha_nacStr, dateFormatter);

            pacienteService.crearPaciente(nombre, apellido, dni, num_afiliado, fecha_nac);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Paciente creado exitosamente"));
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
