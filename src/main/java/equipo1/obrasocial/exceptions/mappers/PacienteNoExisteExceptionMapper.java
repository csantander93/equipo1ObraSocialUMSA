package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.PacienteNoExisteException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PacienteNoExisteExceptionMapper implements ExceptionMapper<PacienteNoExisteException> {

    public PacienteNoExisteExceptionMapper() {
        // Constructor sin argumentos
    }
	
    @Override
    public Response toResponse(PacienteNoExisteException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
}