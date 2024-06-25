package equipo1.obrasocial.exceptions;

public class NoHayEspecialidadesCargadasException extends RuntimeException {
	
    public NoHayEspecialidadesCargadasException() {
        super("No hay especialidades cargadas");
    }

}
