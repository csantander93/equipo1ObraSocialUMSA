package equipo1obrasocial.services;

import java.time.LocalTime;

import equipo1obrasocial.converters.TurnoConverter;
import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.TurnoRepository;
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
	public boolean crearTurno(TurnoDTOMedicoPaciente dto) throws Exception {
		
		Medico medico = medicoRepository.findById(dto.getIdMedico());
		Paciente paciente = pacienteRepository.findById(dto.getIdPaciente());
		
		LocalTime horaDelTurno = dto.getFecha_hora().toLocalTime();
		
		if( (horaDelTurno.isAfter(medico.getAtencionDesde()) || horaDelTurno.equals(medico.getAtencionDesde()))  && 
				(horaDelTurno.isBefore(medico.getAtencionHasta() ) || horaDelTurno.equals(medico.getAtencionHasta()))) {
			
			for(Turno t : medico.getTurnos()) {
				if(t.getFecha_hora().equals(dto.getFecha_hora())) {
					throw new Exception("El horario que estas queriendo crear un turno se encuentra ocupado");
				}
			}	
			
		} else {
			throw new Exception ("El medico no atiende en el horario indicado");
		}
		
		Turno turno = TurnoConverter.convertToEntity(dto, medico, paciente);		

		turnoRepository.persist(turno);
		
		return true;
	}

<<<<<<< HEAD
	 @Override
	 @Transactional
	 public boolean actualizarTurno(TurnoActualizarDTORequest dto) throws Exception {
	        Turno turno = turnoRepository.findById(dto.getIdTurno());
	        
	        System.out.println(turno);
	        
	        if (turno == null) {
	            throw new Exception("El turno no existe");
	        }

	        Medico medicoNuevo = medicoRepository.findById(dto.getIdMedicoNuevo());
	        if (medicoNuevo == null) {
	            throw new Exception("El nuevo médico no existe");
	        }

	        LocalTime horaNuevaTurno = dto.getFechaHoraNueva().toLocalTime();

	        if ((horaNuevaTurno.isAfter(medicoNuevo.getAtencionDesde()) || horaNuevaTurno.equals(medicoNuevo.getAtencionHasta())) &&
	            (horaNuevaTurno.isBefore(medicoNuevo.getAtencionHasta()) || horaNuevaTurno.equals(medicoNuevo.getAtencionHasta()))) {

	            for (Turno t : medicoNuevo.getTurnos()) {
	                if (t.getFecha_hora().equals(dto.getFechaHoraNueva())) {
	                    throw new Exception("El horario que estas queriendo crear un turno se encuentra ocupado");
	                }
	            }    
	        } else {
	            throw new Exception("El medico no atiende en el horario indicado");
	        }

	        turno.setMedico(medicoNuevo);
	        turno.setFecha_hora(dto.getFechaHoraNueva());
	        turno.setMotivoConsulta(dto.getNuevoMotivoConsulta());
	        
	        turnoRepository.persist(turno);
	        
	        return true;
	    }

=======
	@Override
	@Transactional
	public boolean eliminarTurno(TurnoEliminarDTORequest dto) throws Exception {
		
        Turno turno = turnoRepository.findById(dto.getIdTurno());

        if (turno == null) {
            throw new Exception("El turno no existe");
        }

        // Eliminar ??
        turnoRepository.delete(turno);
        return true;
	    
        // o cambiar estado?? actualizar?
	    /** 
	    turno.setActivo(false); 
	    turnoRepository.persist(turno);
	    return true;
	    **/
	    
	    
        
	}
>>>>>>> rama_celina
}
