package equipo1.obrasocial.exceptions;

public class UserPassIncorrectException extends RuntimeException {
	
    public UserPassIncorrectException() {
        super("Usuario y/o contraseña incorrecto");
    }

}
