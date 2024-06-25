package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.NoHayEspecialidadesCargadasException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NoHayEspecialidadesCargadasExceptionMapper implements ExceptionMapper <NoHayEspecialidadesCargadasException>{

    @Override
    public Response toResponse(NoHayEspecialidadesCargadasException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
	
}
