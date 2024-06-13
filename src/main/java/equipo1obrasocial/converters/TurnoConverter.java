package equipo1obrasocial.converters;

import equipo1obrasocial.dtos.request.TurnoDTOMedico;
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
        return turno;
    }
    
    public static Turno convertToEntity(TurnoDTOMedico dto, Medico medico) {
        Turno turno = new Turno();
        turno.setMedico(medico);
        turno.setFecha_hora(dto.getFecha_hora());
        return turno;
    }


}
