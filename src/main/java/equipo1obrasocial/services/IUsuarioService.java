package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.UsuarioDTOLogin;
import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.dtos.response.UsuarioDTOResponse;

public interface IUsuarioService {

	public boolean crearUsuario(UsuarioDTORequest dto);
	
	public UsuarioDTOResponse traerUsuarioLogin(UsuarioDTOLogin dtoLogin);
	
}
