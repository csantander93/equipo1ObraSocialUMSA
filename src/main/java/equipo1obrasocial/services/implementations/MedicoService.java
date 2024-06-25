package equipo1obrasocial.services.implementations;

import java.time.LocalTime;
import java.util.List;

import equipo1.obrasocial.exceptions.EspecialidadSinMedicosException;
import equipo1obrasocial.converters.MedicoConverter;
import equipo1obrasocial.dtos.response.MedicoDTOResponse;
import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.entities.Especialidad;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.repositories.ClinicaRepository;
import equipo1obrasocial.repositories.EspecialidadRepository;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.services.IMedicoService;
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
	
	@Inject 
	private MedicoConverter medicoConverter;

	@Override
	@Transactional
	public boolean crearMedico(String nombre, String apellido, String matricula, LocalTime atencionDesde, LocalTime atencionHasta,
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

	@Override
	/**
	 * Este método permite traer a la vista una lista de los medicos, en conjunto a atributos importantes a considerar de cada uno de
	 * ellos, por eso es que trae un DTO con datos importantes de cada uno
	 * @return una lista de dto's con atributos importantes de los médicos, como su nombre y apellido, nombre de la especialidad, 
	 * horarios de consulta, y la ubicación de la consulta
	 * @throws Exception("No hay medicos que mostrar") si no hay medicos cargados en la BD.
	 */
    public List<MedicoDTOResponse> getCartilla() throws Exception {

        List<Medico> medicos = medicoRepository.findAll().list();
        
        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos que mostrar.");
        }

        return medicoConverter.convertToDTOList(medicos);
    }

	@Override
	public List<MedicoDTOResponse> traerMedicosPorIdEspecialidad(long idEspecialidad) {
		
		List<Medico> medicos = medicoRepository.findMedicosByEspecialidadId(idEspecialidad);
		
		if(medicos == null || medicos.isEmpty()) {
			throw new EspecialidadSinMedicosException();
		}
		
		
		return medicoConverter.convertToDTOList(medicos);
	}
}
