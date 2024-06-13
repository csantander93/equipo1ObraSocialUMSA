package equipo1.obrasocial.exceptions;

public class TurnoFueraDeHorarioException extends RuntimeException {
	
    public TurnoFueraDeHorarioException() {
        super("El médico no da turnos en el horario indicado");
    }
}
