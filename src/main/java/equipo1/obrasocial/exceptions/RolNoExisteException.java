package equipo1.obrasocial.exceptions;

public class RolNoExisteException extends RuntimeException {
	
    public RolNoExisteException() {
        super("El rol de usuario no existe");
    }

}