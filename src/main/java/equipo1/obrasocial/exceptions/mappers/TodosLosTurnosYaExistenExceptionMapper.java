package equipo1.obrasocial.exceptions.mappers;

import equipo1.obrasocial.exceptions.TodosLosTurnosYaExistenException;
import equipo1obrasocial.util.Mensaje;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TodosLosTurnosYaExistenExceptionMapper implements ExceptionMapper<TodosLosTurnosYaExistenException> {

    @Override
    public Response toResponse(TodosLosTurnosYaExistenException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new Mensaje(exception.getMessage()))
                .build();
    }
}
