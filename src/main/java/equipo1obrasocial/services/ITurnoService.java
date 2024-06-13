package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedico;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;

public interface ITurnoService {
	
<<<<<<< HEAD
	public boolean crearTurnoConPaciente(TurnoDTOMedicoPaciente dto) throws Exception;
	public boolean crearTurnoSinPaciente(TurnoDTOMedico dto) throws Exception;

=======
	public boolean crearTurnoConPaciente(TurnoDTOMedicoPaciente dto) ;
	public boolean crearTurnoSinPaciente(TurnoDTOMedico dto) ;
>>>>>>> rama_cris
	
	public boolean actualizarTurno(TurnoActualizarDTORequest dto) ;
	public boolean eliminarTurno(TurnoEliminarDTORequest dto) ;
	boolean darBajaTurno(TurnoEliminarDTORequest dto) ;
}
