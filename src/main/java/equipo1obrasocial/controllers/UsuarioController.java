package equipo1obrasocial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import equipo1obrasocial.dtos.request.UsuarioDTOLogin;
import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.dtos.response.UsuarioDTOResponse;
import equipo1obrasocial.services.IUsuarioService;
import equipo1obrasocial.util.Mensaje;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
@RequestScoped
@Api(tags = "Usuario Controller", description = "Operaciones relacionadas con la gestión de Usuarios")
public class UsuarioController {

	@Inject
	private IUsuarioService usuarioService;
	

    @ApiOperation(value = "Registrar un nuevo usuario para un paciente o medico determinado", notes = "Crea un nuevo usuario para un médico o paciente")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuario creado exitosamente"),
        @ApiResponse(code = 400, message = "Error al crear el usuario")
        })
    @POST
    @Path("/registro")
    public Response crearUsuario(UsuarioDTORequest dto) throws Exception {
        usuarioService.crearUsuario(dto);
        Mensaje mensaje = new Mensaje("Se creó el usuario exitosamente");
        return Response.status(Response.Status.CREATED).entity(mensaje).build();
    }
	
	@POST
	@Path("/login")
    public Response login(@RequestBody UsuarioDTOLogin dtoLogin) {

            UsuarioDTOResponse response = usuarioService.traerUsuarioLogin(dtoLogin);
            return Response.ok(response).build();

    }
}
