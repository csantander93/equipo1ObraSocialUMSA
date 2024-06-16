package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.TurnoFueraDeHorarioException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TurnoFueraDeHorarioExceptionMapper implements ExceptionMapper<TurnoFueraDeHorarioException> {

    @Override
    public Response toResponse(TurnoFueraDeHorarioException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
}
