package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.UsuarioExisteException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UsuarioExisteExceptionMapper implements ExceptionMapper<UsuarioExisteException> {

    public UsuarioExisteExceptionMapper() {
        // Constructor sin argumentos
    }

	
    @Override
    public Response toResponse(UsuarioExisteException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
}

