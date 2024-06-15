package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFecha;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;

public interface ITurnoService {
	
	public boolean crearTurnoConPaciente(TurnoDTOMedicoPaciente dto) ;
	public boolean crearTurnoSinPaciente(TurnoDTOMedicoFechaHora dto) ;
	public boolean crearTurnosMedicoFechaCada20Min(TurnoDTOMedicoFecha dto);
	public boolean actualizarTurno(TurnoActualizarDTORequest dto) ;
	public boolean eliminarTurno(TurnoEliminarDTORequest dto) ;
	boolean darBajaTurno(TurnoEliminarDTORequest dto) ;
}
