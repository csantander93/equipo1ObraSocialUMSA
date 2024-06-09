package equipo1obrasocial.services.implementations;

import java.time.LocalDate;

import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.services.IPacienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PacienteService implements IPacienteService {

	@Inject
	private PacienteRepository pacienteRepository;
	
	@Override
	@Transactional
	public boolean crearPaciente(String nombre, String apellido, String dni, String num_afiliado, LocalDate fecha_nac) throws Exception{

		if(pacienteRepository.findByDni(dni) != null) {
			
			throw new Exception("El paciente con DNI " + dni + " ya existe.");
		}
		
		if(pacienteRepository.findByNumAfiliado(num_afiliado) != null) {
			
			throw new Exception("El paciente con número de afiliado " + num_afiliado + " ya existe.");
		}
		
		Paciente paciente = new Paciente();
		
		paciente.setNombre(nombre);
		
		paciente.setApellido(apellido);
		
		paciente.setDni(dni);
		
		paciente.setNum_afiliado(num_afiliado);
		
		paciente.setFecha_nac(fecha_nac);
		
		pacienteRepository.persist(paciente);
		
		return true;
	}

}
