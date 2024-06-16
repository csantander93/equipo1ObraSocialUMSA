package equipo1obrasocial.converters;

import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Usuario;

public class UsuarioConverter {

    public static Usuario convertToEntity(UsuarioDTORequest dto, Medico medico) {
        Usuario usuario = new Usuario();
        usuario.setMedico(medico);
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRolUsuario(dto.getRolUsuario());
        return usuario;
    }

    public static Usuario convertToEntity(UsuarioDTORequest dto, Paciente paciente) {
        Usuario usuario = new Usuario();
        usuario.setPaciente(paciente);
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRolUsuario(dto.getRolUsuario());
        return usuario;
    }
}
