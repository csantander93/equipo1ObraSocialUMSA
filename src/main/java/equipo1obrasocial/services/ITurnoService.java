package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;

public interface ITurnoService {
	
	public boolean crearTurno(TurnoDTOMedicoPaciente dto) throws Exception;

}
