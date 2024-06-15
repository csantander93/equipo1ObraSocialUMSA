package equipo1.obrasocial.exceptions;

public class HorarioNoDefinidoException extends RuntimeException {
	
    public HorarioNoDefinidoException() {
        super("Horario de atención no definido");
    }

}
