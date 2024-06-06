package equipo1obrasocial.services;

import java.sql.Time;

public interface IMedicoService {

	public boolean crearMedico(String nombre, String apellido, String matricula, Time atencionDesde, Time atencionHasta, String especialidad, long idClinica) throws Exception;
	
}
