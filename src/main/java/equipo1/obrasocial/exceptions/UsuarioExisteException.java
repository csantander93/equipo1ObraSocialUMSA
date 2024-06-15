package equipo1.obrasocial.exceptions;

public class UsuarioExisteException extends RuntimeException {

    public UsuarioExisteException() {
        super("Ya existe un usuario con ese email.");
    }
}
