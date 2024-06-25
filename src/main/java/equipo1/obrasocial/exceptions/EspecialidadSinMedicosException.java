package equipo1.obrasocial.exceptions;

public class EspecialidadSinMedicosException extends RuntimeException {
	
    public EspecialidadSinMedicosException() {
        super("No existen medicos para la especialidad indicada por id");
    }

}
