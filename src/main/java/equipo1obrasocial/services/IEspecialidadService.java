package equipo1obrasocial.services;

import java.util.List;

import equipo1obrasocial.dtos.response.EspecialidadDTOResponse;

public interface IEspecialidadService {
	
	public boolean crearEspecialidad(String nombre) throws Exception;
	public List<EspecialidadDTOResponse> traerTodasEspecialidades();

}
