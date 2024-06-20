package equipo1.obrasocial.exceptions;

public class PacienteRegistradoConMailException extends RuntimeException {
	
    public PacienteRegistradoConMailException() {
        super("Ya existe un mail registrado con el dni ingresado");
    }

}
