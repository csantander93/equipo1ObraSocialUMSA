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
	
	@Override
	@Transactional
	public boolean crearUsuario(UsuarioDTORequest dto) {
		
		if(usuarioRepository.findByEmail(dto.getEmail()) != null) {
			throw new UsuarioExisteException();
		}
		
		if (!"Paciente".equals(dto.getRolUsuario()) && !"Medico".equals(dto.getRolUsuario())) {
		    throw new RolNoExisteException();
		}


		if("Paciente".equals(dto.getRolUsuario())) {
			
			Paciente paciente = pacienteRepository.findByDni(dto.getDni());
			
			if(paciente == null) {
				throw new PacienteNoExisteException();
			}
			
			Usuario usuario = UsuarioConverter.convertToEntity(dto, paciente);
			
			paciente.setUsuario(usuario);
			
			pacienteRepository.persist(paciente);
			usuarioRepository.persist(usuario);
		}
		
		if("Medico".equals(dto.getRolUsuario())) {
			
			Medico medico = medicoRepository.findByMatricula(dto.getMatricula());
			
			if(medico == null) {
				throw new MedicoNoExisteException();
			}
			
			Usuario usuario = UsuarioConverter.convertToEntity(dto, medico);
			
			medico.setUsuario(usuario);
			
			medicoRepository.persist(medico);
			usuarioRepository.persist(usuario);
		}
		
		return true;
	}

}
