package equipo1.obrasocial.exceptions;

public class TodosLosTurnosYaExistenException extends RuntimeException {
	
    public TodosLosTurnosYaExistenException() {
        super("Para esta fecha ya se registraron todos los turnos");
    }

}
