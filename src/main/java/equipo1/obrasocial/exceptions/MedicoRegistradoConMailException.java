package equipo1.obrasocial.exceptions;

public class MedicoRegistradoConMailException extends RuntimeException {

    public MedicoRegistradoConMailException() {
        super("Ya existe un mail registrado con la matricula ingresada");
    }
	
}
