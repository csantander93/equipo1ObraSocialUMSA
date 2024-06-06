package equipo1obrasocial.services;

import java.sql.Time;

import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.entities.Especialidad;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.repositories.ClinicaRepository;
import equipo1obrasocial.repositories.EspecialidadRepository;
import equipo1obrasocial.repositories.MedicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MedicoService implements IMedicoService {

	@Inject
	private MedicoRepository medicoRepository;
	
	@Inject
	private EspecialidadRepository especialidadRepository;
	
	@Inject
	private ClinicaRepository clinicaRepository;

	@Override
	@Transactional
	public boolean crearMedico(String nombre, String apellido, String matricula, Time atencionDesde, Time atencionHasta,
			String nombreEspecialidad, long idClinica) throws Exception {
		
		Especialidad especialidad = especialidadRepository.findByNombre(nombreEspecialidad);
		
		Clinica clinica = clinicaRepository.findById(idClinica);
		
		if(medicoRepository.findByMatricula(matricula) != null) {
			
			throw new Exception("La matrícula " + matricula + " ya está registrada.");
		}
		
		if(especialidad == null) {
			
			throw new Exception("La especialidad " + nombreEspecialidad + " no está registrada.");
		}
		
		if(clinica == null) {
			
			throw new Exception("No hay clínicas registradas con el id " + idClinica + ".");
		}
		
		Medico medico = new Medico();
		
		medico.setNombre(nombre);
		
		medico.setApellido(apellido);
		
		medico.setMatricula(matricula);
		
		medico.setAtencionDesde(atencionDesde);
		
		medico.setAtencionHasta(atencionHasta);
		
		medico.setEspecialidad(especialidad);
		
		medico.setClinica(clinica);
		
		medicoRepository.persist(medico);
		
		return true;
	}

}
