package equipo1obrasocial.services;

import java.time.LocalTime;
import java.util.List;

import equipo1obrasocial.dtos.response.MedicoDTOResponse;

public interface IMedicoService {

	public boolean crearMedico(String nombre, String apellido, String matricula, LocalTime atencionDesde, LocalTime atencionHasta, String especialidad, long idClinica) throws Exception;
	
	public List<MedicoDTOResponse> getCartilla() throws Exception;
	
	public List<MedicoDTOResponse> traerMedicosPorIdEspecialidad(long idEspecialidad);
}
