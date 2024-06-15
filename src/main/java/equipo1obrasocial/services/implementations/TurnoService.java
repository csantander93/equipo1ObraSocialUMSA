package equipo1obrasocial.services.implementations;

import java.time.LocalDateTime;
import java.time.LocalTime;

import equipo1.obrasocial.exceptions.HorarioNoDefinidoException;
import equipo1.obrasocial.exceptions.MedicoNoExisteException;
import equipo1.obrasocial.exceptions.TurnoFueraDeHorarioException;
import equipo1.obrasocial.exceptions.TurnoNoExisteException;
import equipo1.obrasocial.exceptions.TurnoOcupadoException;
import equipo1obrasocial.converters.TurnoConverter;
import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFecha;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.TurnoRepository;
import equipo1obrasocial.services.ITurnoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped

public class TurnoService implements ITurnoService {

	@Inject
	private MedicoRepository medicoRepository;
	
	@Inject
	private PacienteRepository pacienteRepository;
	
	@Inject 
	private TurnoRepository turnoRepository;
	
	@Override
	@Transactional
	public boolean crearTurnoConPaciente(TurnoDTOMedicoPaciente dto) {
		
		Medico medico = medicoRepository.findById(dto.getIdMedico());
		Paciente paciente = pacienteRepository.findById(dto.getIdPaciente());
		
		LocalTime horaDelTurno = dto.getFecha_hora().toLocalTime();
		
		if( (horaDelTurno.isAfter(medico.getAtencionDesde()) || horaDelTurno.equals(medico.getAtencionDesde()))  && 
				(horaDelTurno.isBefore(medico.getAtencionHasta() ) || horaDelTurno.equals(medico.getAtencionHasta()))) {
			
			for(Turno t : medico.getTurnos()) {
				if(t.getFecha_hora().equals(dto.getFecha_hora())) {
					throw new TurnoOcupadoException();
				}
			}	
			
		} else {
			throw new TurnoFueraDeHorarioException();
		}
		
		Turno turno = TurnoConverter.convertToEntity(dto, medico, paciente);
		
		turnoRepository.persist(turno);
        turno.setActivo(true);
		
		return true;
	}
	
	@Override
	@Transactional
	/**
	 *Este metodo crea un turno a un medico determinado pero sin la necesidad de asignar un paciente, ni motivo de consulta, y el turno por defecto
	 *se encuentra en false para que luego en la vista del front el paciente pueda obtener todos los disponibles y se asigne uno
	 * @param id
	 * @return
	 */
	public boolean crearTurnoSinPaciente(TurnoDTOMedicoFechaHora dto) {
		
		Medico medico = medicoRepository.findById(dto.getIdMedico());
		
		LocalTime horaDelTurno = dto.getFecha_hora().toLocalTime();
		
		if( (horaDelTurno.isAfter(medico.getAtencionDesde()) || horaDelTurno.equals(medico.getAtencionDesde()))  && 
				(horaDelTurno.isBefore(medico.getAtencionHasta() ) || horaDelTurno.equals(medico.getAtencionHasta()))) {
			
			for(Turno t : medico.getTurnos()) {
				if(t.getFecha_hora().equals(dto.getFecha_hora())) {
					throw new TurnoOcupadoException();
				}
			}	
			
		} else {
			throw new TurnoFueraDeHorarioException();
		}
		
		Turno turno = TurnoConverter.convertToEntity(dto, medico);
		turno.setActivo(false);
		
		turnoRepository.persist(turno);
		
		return true;
	}

	@Override
	@Transactional
	public boolean crearTurnosMedicoFechaCada20Min(TurnoDTOMedicoFecha dto) {
	    // Obtener el médico por ID
	    Medico medico = medicoRepository.findById(dto.getIdMedico());

	    if (medico == null) {
	        throw new MedicoNoExisteException();
	    }

	    LocalTime horaInicio = medico.getAtencionDesde();
	    LocalTime horaFin = medico.getAtencionHasta();

	    // Verificar que los horarios estén definidos
	    if (horaInicio == null || horaFin == null) {
	        throw new HorarioNoDefinidoException();
	    }

	    // Inicializar las variables de tiempo
	    LocalDateTime fechaHoraActual = dto.getFecha().atTime(horaInicio);
	    LocalDateTime fechaHoraFin = dto.getFecha().atTime(horaFin);

	    // Iterar desde la hora de inicio hasta la hora de fin
	    while (!fechaHoraActual.isAfter(fechaHoraFin)) {
	        LocalDateTime fechaHoraTurno = fechaHoraActual;

	        // Verificar si ya existe un turno para la fecha y hora actual
	        boolean turnoExistente = medico.getTurnos().stream()
	            .anyMatch(turno -> turno.getFecha_hora().equals(fechaHoraTurno));

	        // Si el turno ya existe, continuar con el siguiente intervalo de 20 minutos
	        if (turnoExistente) {
	            fechaHoraActual = fechaHoraActual.plusMinutes(20);
	            continue;
	        }

	        // Convertir el DTO a entidad y persistirlo
	        Turno turno = TurnoConverter.convertToEntity(dto, medico, fechaHoraTurno);
	        turno.setActivo(false);
	        turnoRepository.persist(turno);

	        // Incrementar la hora actual en 20 minutos
	        fechaHoraActual = fechaHoraActual.plusMinutes(20);
	    }

	    return true;
	}



	@Override
	@Transactional
	public boolean eliminarTurno(TurnoEliminarDTORequest dto) {
		
        Turno turno = turnoRepository.findById(dto.getIdTurno());

        if (turno == null) {
            throw new TurnoNoExisteException();
        }

        turnoRepository.delete(turno);
        return true;
	    
	}
	
	@Override
	@Transactional
	public boolean darBajaTurno(TurnoEliminarDTORequest dto) {
		
        Turno turno = turnoRepository.findById(dto.getIdTurno());

        if (turno == null) {
            throw new TurnoNoExisteException();
        }

	    turno.setActivo(false); 
	    turno.setMotivoConsulta("");
	    turno.setPaciente(null);
	    turno.setReceta(null);
	    turnoRepository.persist(turno);
	    return true;
    
	}

	 @Override
	 @Transactional
	 public boolean actualizarTurno(TurnoActualizarDTORequest dto) {
	        Turno turno = turnoRepository.findById(dto.getIdTurno());
	        
	        System.out.println(turno);
	        
	        if (turno == null) {
	            throw new TurnoNoExisteException();
	        }

	        Medico medicoNuevo = medicoRepository.findById(dto.getIdMedicoNuevo());
	        if (medicoNuevo == null) {
	            throw new MedicoNoExisteException();
	        }

	        LocalTime horaNuevaTurno = dto.getFechaHoraNueva().toLocalTime();

	        if ((horaNuevaTurno.isAfter(medicoNuevo.getAtencionDesde()) || horaNuevaTurno.equals(medicoNuevo.getAtencionHasta())) &&
	            (horaNuevaTurno.isBefore(medicoNuevo.getAtencionHasta()) || horaNuevaTurno.equals(medicoNuevo.getAtencionHasta()))) {

	            for (Turno t : medicoNuevo.getTurnos()) {
	                if (t.getFecha_hora().equals(dto.getFechaHoraNueva())) {
	                    throw new TurnoOcupadoException();
	                }
	            }    
	        } else {
	            throw new TurnoFueraDeHorarioException();
	        }

	        turno.setMedico(medicoNuevo);
	        turno.setFecha_hora(dto.getFechaHoraNueva());
	        turno.setMotivoConsulta(dto.getNuevoMotivoConsulta());
	        
	        turnoRepository.persist(turno);
	        
	        return true;
	    }
	
}

