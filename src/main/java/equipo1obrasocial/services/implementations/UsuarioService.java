package equipo1obrasocial.services.implementations;

import equipo1.obrasocial.exceptions.MedicoNoExisteException;
import equipo1.obrasocial.exceptions.PacienteNoExisteException;
import equipo1.obrasocial.exceptions.RolNoExisteException;
import equipo1.obrasocial.exceptions.UsuarioExisteException;
import equipo1obrasocial.converters.UsuarioConverter;
import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Usuario;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.UsuarioRepository;
import equipo1obrasocial.services.IUsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService implements IUsuarioService {

	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private MedicoRepository medicoRepository;
	
	@Inject
	private PacienteRepository pacienteRepository;
	
    @Inject
    private UsuarioConverter usuarioConverter;
	
	@Override
	@Transactional
    public boolean crearUsuario(UsuarioDTORequest dto) throws Exception {
        if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
            throw new UsuarioExisteException();
        }

        Usuario usuario;
        if (dto.getRolUsuario().equals("Paciente")) {
            Paciente paciente = pacienteRepository.findByDni(dto.getIdentificador());

            if (paciente == null) {
                throw new PacienteNoExisteException();
            }

            usuario = usuarioConverter.convertToEntity(dto, paciente);
            paciente.setUsuario(usuario);
            pacienteRepository.persist(paciente);
            usuarioRepository.persist(usuario);

        } else if (dto.getRolUsuario().equals("Medico")) {
            Medico medico = medicoRepository.findByMatricula(dto.getIdentificador());
            if (medico == null) {
                throw new MedicoNoExisteException();
            }

            usuario = usuarioConverter.convertToEntity(dto, medico);
            medico.setUsuario(usuario);
            medicoRepository.persist(medico);
            usuarioRepository.persist(usuario);

        } else {
            throw new Exception("Rol de usuario no válido");
        }

        return true;
    }

}
