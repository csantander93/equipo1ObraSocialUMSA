package equipo1obrasocial.services.implementations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import equipo1.obrasocial.exceptions.HorarioNoDefinidoException;
import equipo1.obrasocial.exceptions.MedicoNoExisteException;
import equipo1.obrasocial.exceptions.PacienteNoExisteException;
import equipo1.obrasocial.exceptions.TurnoFueraDeHorarioException;
import equipo1.obrasocial.exceptions.TurnoNoExisteException;
import equipo1.obrasocial.exceptions.TurnoOcupadoException;
import equipo1obrasocial.converters.TurnoConverter;
import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOAsignarPaciente;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFecha;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1obrasocial.dtos.response.TurnoDTOResponse;
import equipo1obrasocial.dtos.response.TurnoDTOVistaResponse;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.entities.Usuario;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.TurnoRepository;
import equipo1obrasocial.repositories.UsuarioRepository;
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
	
	@Inject 
	private TurnoConverter turnoConverter;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional
	/**
	 * Este método crea un turno para un médico específico con un paciente asignado y en una fecha y hora determinadas,
	 * siempre que cumpla con las condiciones de horario de atención del médico y no haya conflictos con otros turnos existentes.
	 *
	 * Especialmente útil para casos donde se requiere asignar un horario específico con un paciente indicado.
	 *
	 * @param dto el objeto de transferencia de datos que contiene el ID del médico, el ID del paciente y la fecha y hora del turno a crear.
	 * @return true si el turno se creó correctamente, false en caso contrario.
	 * @throws TurnoOcupadoException si ya existe un turno para la fecha y hora especificada.
	 * @throws TurnoFueraDeHorarioException si la hora del turno está fuera del horario de atención del médico.
	 * @throws MedicoNoExisteException si el médico especificado por el ID no existe en el sistema.
	 * @throws PacienteNoExisteException si el paciente especificado por el ID no existe en el sistema.
	 */
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
		
		Turno turno = turnoConverter.convertToEntity(dto, medico, paciente);
		
		turnoRepository.persist(turno);
        turno.setActivo(true);
		
		return true;
	}
	
	@Override
	@Transactional
	/**
	 * Este método crea un turno para un médico determinado sin asignar un paciente ni un motivo de consulta.
	 * El turno creado por defecto se encuentra inactivo (false) para que luego, en la vista del frontend, 
	 * el paciente pueda obtener todos los turnos disponibles y asignarse uno.
	 *
	 * @param dto el objeto de transferencia de datos que contiene la información del turno a crear,
	 *            incluyendo el ID del médico y la fecha y hora del turno.
	 * @return true si el turno se creó correctamente, false en caso contrario.
	 * @throws TurnoOcupadoException si ya existe un turno en la fecha y hora especificada.
	 * @throws TurnoFueraDeHorarioException si la hora del turno está fuera del horario de atención del médico.
	 * @throws MedicoNoExisteException si el médico especificado no existe.
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
		
		Turno turno = turnoConverter.convertToEntity(dto, medico);
		turno.setActivo(false);
		
		turnoRepository.persist(turno);
		
		return true;
	}
	
	/**
	 * Este método crea automáticamente una serie de turnos para un médico en intervalos de 15 minutos,
	 * comenzando desde la hora de inicio de atención del médico hasta la hora de fin de atención, en una fecha específica.
	 *
	 * Especialmente útil para programar turnos más frecuentes dentro del horario de atención del médico.
	 *
	 * @param dto el objeto de transferencia de datos que contiene el ID del médico y la fecha para la cual se deben crear los turnos.
	 * @return true si se crearon todos los turnos correctamente, false en caso contrario.
	 * @throws MedicoNoExisteException si el médico especificado por el ID no existe en el sistema.
	 * @throws HorarioNoDefinidoException si los horarios de inicio o fin de atención del médico no están definidos.
	 *                                    Esto impide determinar el rango de horas válidas para la creación de turnos.
	 */
	@Override
	@Transactional
	public boolean crearTurnosMedicoFechaCada15Min(TurnoDTOMedicoFecha dto) {
<<<<<<< HEAD
	    
=======
	 
>>>>>>> rama_cris
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario());
		
		if(usuario.getMedico() == null) {
			throw new MedicoNoExisteException();
		}
		
	    Medico medico = medicoRepository.findById(usuario.getMedico().getId());

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

	        // Si el turno ya existe, continuar con el siguiente intervalo de 15 minutos
	        if (turnoExistente) {
	            fechaHoraActual = fechaHoraActual.plusMinutes(15);
	            continue;
	        }

	        // Convertir el DTO a entidad de Turno y persistirlo
	        Turno turno = turnoConverter.convertToEntity(dto, medico, fechaHoraTurno);
	        turno.setActivo(false);
	        turnoRepository.persist(turno);

	        // Incrementar la hora actual en 15 minutos
	        fechaHoraActual = fechaHoraActual.plusMinutes(15);
	    }

	    return true;
	}


	@Override
	@Transactional
	/**
	 * Este método crea automáticamente una serie de turnos para un médico en intervalos de 20 minutos,
	 * comenzando desde la hora de inicio de atención del médico hasta la hora de fin de atención, en una fecha específica.
	 *
	 * @param dto el objeto de transferencia de datos que contiene el ID del médico y la fecha para la cual se deben crear los turnos.
	 * @return true si se crearon todos los turnos correctamente, false en caso contrario.
	 * @throws MedicoNoExisteException si el médico especificado por el ID no existe en el sistema.
	 * @throws HorarioNoDefinidoException si los horarios de inicio o fin de atención del médico no están definidos.
	 *                                    Esto impide determinar el rango de horas válidas para la creación de turnos.
	 */
	public boolean crearTurnosMedicoFechaCada20Min(TurnoDTOMedicoFecha dto) {
<<<<<<< HEAD
	    
=======
	    // Obtener el médico por ID
>>>>>>> rama_cris
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario());
		
		if(usuario.getMedico() == null) {
			throw new MedicoNoExisteException();
		}
		
	    Medico medico = medicoRepository.findById(usuario.getMedico().getId());

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
	        Turno turno = turnoConverter.convertToEntity(dto, medico, fechaHoraTurno);
	        turno.setActivo(false);
	        turnoRepository.persist(turno);

	        // Incrementar la hora actual en 20 minutos
	        fechaHoraActual = fechaHoraActual.plusMinutes(20);
	    }

	    return true;
	}

	@Override
	@Transactional
	/**
	 * Este método permite eliminar un turno de la BD, solamente asignando el id del turno que se quiere eliminar
	 * @param dto con el id del turno a eliminar.
	 * @return true si se eliminó correctamente el turno.
	 * @throws TurnoNoExisteException si el turno que se dio por ID no existe.
	 */
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
		/**
		 * Este método permite actualizar datos de un turno ya existente, como la fecha, el medico o un nuevo motivo de consulta
		 * @param dto con los datos a actualizar y el id del turno al que se quiere modificar
		 * @return true si se actualizó correctamente.
		 * @throws TurnoNoExisteException si el turno que se dio por ID no existe.
		 * @throws MedicoNoExisteException si el id del medico brindado en el dto no está asociado a ningún medico cargado en la BD.
		 * @throws TurnoOcupadoException si existe un turno previamente asignado a la fecha y horario solicitada
		 * @throws TurnoFueraDeHorarioException si se quiere asignarle un turno a un medico, pero este es pretendido fuera de sus rangos horarios.
		 */
	 public boolean actualizarTurno(TurnoActualizarDTORequest dto) {
	        Turno turno = turnoRepository.findById(dto.getIdTurno());
	        
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

	@Override
	@Transactional
    public List<TurnoDTOResponse> traerTurnosDisponiblesPorMedico(long idMedico) {
		
		if(medicoRepository.findById(idMedico) == null) {
			throw new MedicoNoExisteException();
		}
		
        List<Turno> turnos = turnoRepository.findByMedicoIdAndActivo(idMedico);
        List<TurnoDTOResponse> dtos = new ArrayList();
        for (Turno t : turnos) {
        	TurnoDTOResponse dto = turnoConverter.convertToDTO(t);
        	dtos.add(dto);
        }
        return dtos;
    }

	@Override
	@Transactional
	public boolean asignarTurno(TurnoDTOAsignarPaciente dto) {
        Turno turno = turnoRepository.findById(dto.getIdTurno());
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario());
        Paciente paciente = usuario.getPaciente();
        
        if (turno == null) {
            throw new TurnoNoExisteException();
        }
        
        if (paciente == null) {
        	throw new PacienteNoExisteException();
        }

        turno.setPaciente(paciente);
        turno.setMotivoConsulta(dto.getMotivoConsulta());
        turno.setActivo(true);
        
        turnoRepository.persist(turno);
        
        return true;
	}

	@Override
	public List<TurnoDTOVistaResponse> traerTurnosPorIdUsuario(long idUsuario) {
	    Usuario usuario = usuarioRepository.findById(idUsuario);
	    
	    List<Turno> turnos = new ArrayList<>();

	    if (usuario.getPaciente() != null) {
	        Paciente paciente = usuario.getPaciente();
	        Paciente pacienteExistente = pacienteRepository.findById(paciente.getId());
	        if (pacienteExistente == null) {
	            throw new PacienteNoExisteException();
	        }
	        turnos = turnoRepository.findByPacienteIdAndActivo(paciente.getId());
	    } else if (usuario.getMedico() != null) {
	        Medico medico = usuario.getMedico();
	        Medico medicoExistente = medicoRepository.findById(medico.getId());
	        if (medicoExistente == null) {
	            throw new MedicoNoExisteException();
	        }
	        turnos = turnoRepository.findByMedicoIdAndActivoUser(medico.getId());
	    }

	    List<TurnoDTOVistaResponse> dtos = new ArrayList<>();
	    for (Turno turno : turnos) {
	        TurnoDTOVistaResponse dto = turnoConverter.convertToDTOVista(turno);
	        dtos.add(dto);
	    }
	    return dtos;
	}

	
}


