package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedico;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;

public interface ITurnoService {
	
	public boolean crearTurnoConPaciente(TurnoDTOMedicoPaciente dto) throws Exception;
	public boolean crearTurnoSinPaciente(TurnoDTOMedico dto) throws Exception;

	
	public boolean actualizarTurno(TurnoActualizarDTORequest dto) throws Exception;
	public boolean eliminarTurno(TurnoEliminarDTORequest dto) throws Exception;
	boolean darBajaTurno(TurnoEliminarDTORequest dto) throws Exception;
}
