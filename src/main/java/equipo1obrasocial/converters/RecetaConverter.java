package equipo1obrasocial.converters;

import equipo1obrasocial.dtos.request.RecetaDTORequest;
import equipo1obrasocial.dtos.response.RecetaDTOResponse;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Receta;
import equipo1obrasocial.entities.Turno;


public class RecetaConverter {

    public static Receta convertToEntity(RecetaDTORequest dto, Medico medico, Paciente paciente, Turno turno) {
		
    	Receta receta = new Receta();
    
		receta.setClinica(medico.getClinica().getNombre());
		receta.setDiagnostico(dto.getDiagnostico());
		receta.setFecha(dto.getFecha());
		receta.setTratamiento(dto.getTratamiento());
		receta.setMedico(medico);
		receta.setPaciente(paciente);
		receta.setTurno(turno);
		
		return receta;
    }
    
    public static RecetaDTOResponse convertToDTO(Receta receta) {
        RecetaDTOResponse recetaDTO = new RecetaDTOResponse();

        recetaDTO.setDiagnostico(receta.getDiagnostico());
        recetaDTO.setFecha(receta.getFecha());
        recetaDTO.setIdReceta(receta.getId());
        recetaDTO.setNombreClinica(receta.getMedico().getClinica().getNombre());
        recetaDTO.setDireccionClinica(receta.getMedico().getClinica().getDireccion());
        recetaDTO.setEspecialidadMedico(receta.getMedico().getEspecialidad().getNombreEspecialidad());
        recetaDTO.setTratamiento(receta.getTratamiento());
        recetaDTO.setNombreCompletoMedico(receta.getMedico().getNombre().concat(" " + receta.getMedico().getApellido()));

        return recetaDTO;
    }
}
