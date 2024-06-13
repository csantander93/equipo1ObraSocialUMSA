package equipo1.obrasocial.exceptions;

public class TurnoNoExisteException extends RuntimeException {

    public TurnoNoExisteException() {
        super("El turno no existe");
    }
}
