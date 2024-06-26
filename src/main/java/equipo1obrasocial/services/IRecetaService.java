package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.RecetaDTORequest;
import equipo1obrasocial.dtos.response.RecetaDTOResponse;

public interface IRecetaService {

	public boolean crearReceta(RecetaDTORequest dto) throws Exception;
	
	public RecetaDTOResponse traerRecetaPorTurno(long idTurno) throws Exception;
	
	public RecetaDTOResponse traerRecetaPorIDReceta(long idReceta);
}
