package equipo1.obrasocial.exceptions;

public class MedicoNoExisteException extends RuntimeException {
	
    public MedicoNoExisteException() {
        super("El médico no existe");
    }

}
