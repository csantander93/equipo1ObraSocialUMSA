package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.EspecialidadSinMedicosException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class EspecialidadSinMedicosExceptionMapper implements ExceptionMapper <EspecialidadSinMedicosException>{

    @Override
    public Response toResponse(EspecialidadSinMedicosException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
	
}
