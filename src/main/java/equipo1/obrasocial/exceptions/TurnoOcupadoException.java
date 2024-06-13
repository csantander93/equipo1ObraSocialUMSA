package equipo1.obrasocial.exceptions;

public class TurnoOcupadoException extends RuntimeException {

    public TurnoOcupadoException() {
        super("El horario que estás queriendo crear un turno se encuentra ocupado");
    }
}
