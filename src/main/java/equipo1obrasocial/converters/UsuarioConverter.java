package equipo1obrasocial.converters;

import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.dtos.response.UsuarioDTOResponse;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Usuario;
import equipo1obrasocial.util.PasswordService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioConverter {

    @Inject
    private PasswordService passwordService;

    public Usuario convertToEntity(UsuarioDTORequest dto, Medico medico) {
        Usuario usuario = new Usuario();
        usuario.setMedico(medico);
        usuario.setEmail(dto.getEmail());
        usuario.setRolUsuario(dto.getRolUsuario());
        // Encriptar la contraseña
        usuario.setPassword(passwordService.hashPassword(dto.getPassword()));
        return usuario;
    }

    public Usuario convertToEntity(UsuarioDTORequest dto, Paciente paciente) {
        Usuario usuario = new Usuario();
        usuario.setPaciente(paciente);
        usuario.setEmail(dto.getEmail());
        usuario.setRolUsuario(dto.getRolUsuario());
        // Encriptar la contraseña
        usuario.setPassword(passwordService.hashPassword(dto.getPassword()));
        return usuario;
    }
    
    public UsuarioDTOResponse converToModel(Usuario usuario) {
    	
    	UsuarioDTOResponse usuarioDto = new UsuarioDTOResponse();
    	
    	usuarioDto.setId(usuario.getId());
    	usuarioDto.setEmail(usuario.getEmail());
    	usuarioDto.setRolUsuario(usuario.getRolUsuario());
    	
    	return usuarioDto;
    }
}
