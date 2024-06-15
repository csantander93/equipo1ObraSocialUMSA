package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.RolNoExisteException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RolNoExisteExceptionMapper implements ExceptionMapper<RolNoExisteException> {

    public RolNoExisteExceptionMapper() {
        // Constructor sin argumentos
    }
	
    @Override
    public Response toResponse(RolNoExisteException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
}