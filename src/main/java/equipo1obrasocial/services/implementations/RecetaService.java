package equipo1obrasocial.services.implementations;

import equipo1obrasocial.converters.RecetaConverter;
import equipo1obrasocial.dtos.request.RecetaDTORequest;
import equipo1obrasocial.dtos.response.RecetaDTOResponse;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Receta;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.RecetaRepository;
import equipo1obrasocial.repositories.TurnoRepository;
import equipo1obrasocial.services.IRecetaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RecetaService implements IRecetaService {

	@Inject
	private RecetaRepository recetaRepository;
	
	@Inject
	private TurnoRepository turnoRepository;
	
	@Inject
	private MedicoRepository medicoRepository;
	
	@Inject
	private PacienteRepository pacienteRepository;
	
	@Override
	@Transactional
	public boolean crearReceta(RecetaDTORequest dto) throws Exception {

		Medico medico = medicoRepository.findById(dto.getIdMedico());
		Paciente paciente = pacienteRepository.findById(dto.getIdPaciente());
		Turno turno = turnoRepository.findById(dto.getIdTurno());
		
		if(paciente == null) {
			throw new Exception("El paciente de id " + dto.getIdPaciente() + " no existe.");
		}
		
		if(medico == null) {
			throw new Exception("El medico de id " + dto.getIdMedico() + " no existe.");
		}
		
		if(turno == null) {
			throw new Exception("No existe ningún turno con id " + dto.getIdTurno() + ".");
		}
		
		Receta receta = RecetaConverter.convertToEntity(dto, medico, paciente, turno);
		
		turno.setReceta(receta);
		
		turnoRepository.persist(turno);

		recetaRepository.persist(receta);
		
		return true;
	}

	@Override
	@Transactional
	/**
	 * Este método trae la receta asignada a cada turno médico, mediante un identificador, que es el id del turno,
	 * permite en la vista recibir los datos de la receta, como el id, nombre de la clinica, direccion, la especialidad medica,
	 * la fecha, el diagnostico y el tratamiento, y el nombre completo del médico
	 * @param el id del turno asociado a la receta
	 * @return el dto de receta que trae los datos anteriormente mencionados
	 */
	public RecetaDTOResponse traerRecetaPorTurno(long idTurno) throws Exception{
		
		Receta receta = recetaRepository.findByTurnoId(idTurno);
	
		return RecetaConverter.convertToDTO(receta);
	}

	@Override
	public RecetaDTOResponse traerRecetaPorIDReceta(long idReceta) {

		Receta receta = recetaRepository.findById(idReceta);
		
		return RecetaConverter.convertToDTO(receta);

	}

	
}
