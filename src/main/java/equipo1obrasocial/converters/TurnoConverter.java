package equipo1obrasocial.converters;

import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Turno;

public class TurnoConverter {
	
    public static Turno convertToEntity(TurnoDTOMedicoPaciente dto, Medico medico, Paciente paciente) {
        Turno turno = new Turno();
        turno.setMedico(medico);
        turno.setPaciente(paciente);
        turno.setFecha_hora(dto.getFecha_hora());
        turno.setMotivoConsulta(dto.getMotivoConsulta());
        turno.setActivo(true);
        return turno;
    }
    
    public static Turno convertToEntity(TurnoDTOMedicoPaciente dto) {
        Turno turno = new Turno();
        turno.setFecha_hora(dto.getFecha_hora());
        turno.setMotivoConsulta(dto.getMotivoConsulta());
        turno.setActivo(true);
        return turno;
    }

}
