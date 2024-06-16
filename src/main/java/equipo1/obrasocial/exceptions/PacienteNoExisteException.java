package equipo1.obrasocial.exceptions;

public class PacienteNoExisteException extends RuntimeException {

    public PacienteNoExisteException() {
        super("El paciente no existe.");
    }
}
