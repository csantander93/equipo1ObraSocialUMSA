package equipo1obrasocial.services;

import java.time.LocalTime;

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
		
		Turno turno = new Turno();
		
		turno.setMedico(medico);
		turno.setPaciente(paciente);
		
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
		
		turno.setFecha_hora(dto.getFecha_hora());
		turno.setMotivoConsulta(dto.getMotivoConsulta());
		turno.setActivo(true);		

		turnoRepository.persist(turno);
		
		return true;
	}

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
}
