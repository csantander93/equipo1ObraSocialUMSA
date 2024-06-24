package equipo1obrasocial.controllers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import equipo1obrasocial.dtos.response.MedicoDTOResponse;
import equipo1obrasocial.services.IMedicoService;
import equipo1obrasocial.util.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/medicos")
@RequestScoped
@Api(tags = "Medico Controller", description = "Operaciones relacionadas con la gestión de médicos")
public class MedicoController {

    @Inject
    private IMedicoService medicoService;
    
    @GET
    @Path("/cartillaMedicos")
    @ApiOperation(value = "Obtener cartilla de médicos", notes = "Obtiene una lista de todos los médicos especialistas")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cartilla de médicos obtenida exitosamente"),
        @ApiResponse(code = 400, message = "Error al obtener la cartilla de médicos")
    })
    public Response obtenerCartillaDeMedicos() {
        try {
            List<MedicoDTOResponse> medicos = medicoService.getCartilla();
            return Response.ok(medicos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Mensaje(e.getMessage())).build();
        }
    }

    @POST
    @Path("/crearMedico/{nombre}/{apellido}/{matricula}/{atencionDesde}/{atencionHasta}/{especialidad}/{idClinica}")
    @ApiOperation(value = "Crear un nuevo médico", notes = "Crea un nuevo médico con la información especificada")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Médico creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el médico")
    })
    public ResponseEntity<Object> altaMedico(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido, 
                                             @PathParam("matricula") String matricula, @PathParam("atencionDesde") String atencionDesdeStr, 
                                             @PathParam("atencionHasta") String atencionHastaStr, @PathParam("especialidad") String especialidad, 
                                             @PathParam("idClinica") long idClinica) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
            LocalTime atencionDesde = LocalTime.parse(atencionDesdeStr, formatter);
            LocalTime atencionHasta = LocalTime.parse(atencionHastaStr, formatter);

            medicoService.crearMedico(nombre, apellido, matricula, atencionDesde, atencionHasta, especialidad, idClinica);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Medico creado exitosamente"));
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
