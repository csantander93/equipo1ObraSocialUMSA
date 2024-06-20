package equipo1obrasocial.services.implementations;

import equipo1.obrasocial.exceptions.MedicoNoExisteException;
import equipo1.obrasocial.exceptions.MedicoRegistradoConMailException;
import equipo1.obrasocial.exceptions.PacienteNoExisteException;
import equipo1.obrasocial.exceptions.PacienteRegistradoConMailException;
import equipo1.obrasocial.exceptions.RolNoExisteException;
import equipo1.obrasocial.exceptions.UsuarioExisteException;
import equipo1.obrasocial.exceptions.UserPassIncorrectException;
import equipo1obrasocial.converters.UsuarioConverter;
import equipo1obrasocial.dtos.request.UsuarioDTOLogin;
import equipo1obrasocial.dtos.request.UsuarioDTORequest;
import equipo1obrasocial.dtos.response.UsuarioDTOResponse;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Usuario;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.UsuarioRepository;
import equipo1obrasocial.services.IUsuarioService;
import equipo1obrasocial.util.PasswordService;
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
    
    @Inject
    private PasswordService passwordService;
	
	/**
	 * Este método permite crear un usuario, ya sea para un paciente o médico asociado a la obra social, pero es un método que
	 * desarrollaremos más en la etapa del Front, asi tambien como los otros metodos de UsuarioService, y no le hemos dado mayor
	 *  foco en la primer etapa.
	 * @param un dto con email, password, rol de usuario, y un identificativo para saber si es Medico o Paciente, ya sea
	 * dni o matricula (en la segunda etapa se va a modificar)
	 * @return true si el usuario se creó exitosamente.
	 * @throws UsuarioExisteException si el usuario con ese email ya está cargado en la BD
	 * @throws RolNoExisteException si se ingresa un rol de usuario distinto a Medico o Paciente
	 * @throws PacienteNoExisteException si el paciente que se dió por dni no se encuentra en la BD
	 * @throws MedicoNoExisteException si el medico dado por matricula no se encuentra en la BD
	 */
	@Override
	@Transactional
	public boolean crearUsuario(UsuarioDTORequest dto) {
		
		if(usuarioRepository.findByEmail(dto.getEmail()) != null) {
			throw new UsuarioExisteException();
		}
		
		if (!"Paciente".equals(dto.getRolUsuario()) && !"Medico".equals(dto.getRolUsuario())) {
		    throw new RolNoExisteException();
		}

        Usuario usuario;
        
        if (dto.getRolUsuario().equals("Paciente")) {
        	
            Paciente paciente = pacienteRepository.findByDni(dto.getIdentificador());
            
            if (paciente == null) {
                throw new PacienteNoExisteException();
            } else {
            	if(usuarioRepository.findByPacienteId(paciente.getId()) != null) {
            		throw new PacienteRegistradoConMailException();
            	}
            }
            
            usuario = usuarioConverter.convertToEntity(dto, paciente);
            paciente.setUsuario(usuario);
            pacienteRepository.persist(paciente);
            usuarioRepository.persist(usuario);

        } else if (dto.getRolUsuario().equals("Medico")) {
        	
            Medico medico = medicoRepository.findByMatricula(dto.getIdentificador());
            
            if (medico == null) {
                throw new MedicoNoExisteException();
            } else {
            	if(usuarioRepository.findByMedicoId(medico.getId()) != null) {
            		throw new MedicoRegistradoConMailException();
            	}
            }

            usuario = usuarioConverter.convertToEntity(dto, medico);
            medico.setUsuario(usuario);
            medicoRepository.persist(medico);
            usuarioRepository.persist(usuario);

        } else {
            throw new RolNoExisteException();
        }

        return true;
    }

	@Override
	public UsuarioDTOResponse traerUsuarioLogin(UsuarioDTOLogin dto) {
		
		Usuario usuarioEnt = usuarioRepository.findByEmail(dto.getEmail());
        if (usuarioEnt == null || !passwordService.verifyPassword(dto.getPassword(), usuarioEnt.getPassword())) {
            throw new UserPassIncorrectException();
        }
		
		return usuarioConverter.converToModel(usuarioEnt);
	}

}
