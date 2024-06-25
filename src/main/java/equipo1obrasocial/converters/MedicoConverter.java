package equipo1obrasocial.converters;

import java.util.ArrayList;
import java.util.List;

import equipo1obrasocial.dtos.response.MedicoDTOResponse;
import equipo1obrasocial.entities.Medico;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MedicoConverter {

    public static MedicoDTOResponse convertToDTO(Medico medico) {
        MedicoDTOResponse dto = new MedicoDTOResponse();
        
        String nombreMedico = medico.getNombre().concat(" " + medico.getApellido());
        dto.setNombreMedico(nombreMedico);
        dto.setNombreEspecialidad(medico.getEspecialidad().getNombreEspecialidad());
        dto.setUbicacionConsulta(medico.getClinica().getDireccion());
        dto.setAtencionDesde(medico.getAtencionDesde());
        dto.setAtencionHasta(medico.getAtencionHasta());
        
        return dto;
    }

    public static List<MedicoDTOResponse> convertToDTOList(List<Medico> medicos) {
        List<MedicoDTOResponse> dtos = new ArrayList<>();
        for (Medico medico : medicos) {
            dtos.add(convertToDTO(medico));
        }
        return dtos;
    }
}