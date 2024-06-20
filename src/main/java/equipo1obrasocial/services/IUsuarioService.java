package equipo1obrasocial.services;

import equipo1obrasocial.dtos.request.UsuarioDTORequest;

public interface IUsuarioService {

	public boolean crearUsuario(UsuarioDTORequest dto) throws Exception;
}
