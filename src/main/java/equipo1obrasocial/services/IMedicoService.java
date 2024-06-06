package equipo1obrasocial.services;

import java.time.LocalTime;

public interface IMedicoService {

	public boolean crearMedico(String nombre, String apellido, String matricula, LocalTime atencionDesde, LocalTime atencionHasta, String especialidad, long idClinica) throws Exception;
	
}
