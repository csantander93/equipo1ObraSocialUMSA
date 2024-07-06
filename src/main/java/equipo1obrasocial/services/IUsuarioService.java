package equipo1obrasocial.services;

import java.util.List;

import equipo1obrasocial.dtos.request.UsuarioDTOLogin;
import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.dtos.response.UsuarioDTOResponse;
import equipo1obrasocial.dtos.response.UsuarioPacienteDTOResponse;

public interface IUsuarioService {

	public boolean crearUsuario(UsuarioDTORequest dto);
	
	public UsuarioDTOResponse traerUsuarioLogin(UsuarioDTOLogin dtoLogin);
	
	public List<UsuarioPacienteDTOResponse> traerUsuariosPacientes();
	
}
