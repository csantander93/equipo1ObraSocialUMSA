package equipo1obrasocial.services.implementations;

import java.util.ArrayList;
import java.util.List;

import equipo1.obrasocial.exceptions.NoHayEspecialidadesCargadasException;
import equipo1obrasocial.dtos.response.EspecialidadDTOResponse;
import equipo1obrasocial.entities.Especialidad;
import equipo1obrasocial.repositories.EspecialidadRepository;
import equipo1obrasocial.services.IEspecialidadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EspecialidadService  implements IEspecialidadService{
	
	@Inject
	private EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
	public boolean crearEspecialidad(String nombre) throws Exception {
		
		if(especialidadRepository.findByNombre(nombre) != null) {
			
			throw new Exception ("La especialidad "+ nombre + " que estas queriendo crear ya existe");
		}
		
		Especialidad especialidad = new Especialidad();
		
		especialidad.setNombreEspecialidad(nombre);
		
		especialidadRepository.persist(especialidad);;
		
		return true;
	}

	@Override
	public List<EspecialidadDTOResponse> traerTodasEspecialidades() {
		
		List<Especialidad> especialidades = especialidadRepository.findAllEspecialidades();
		
		if(especialidades.isEmpty()) {
			throw new NoHayEspecialidadesCargadasException();
		}
		
		List<EspecialidadDTOResponse> dtos = new ArrayList();
		
		for(Especialidad e : especialidades) {
			EspecialidadDTOResponse dto = new EspecialidadDTOResponse();
			dto.setIdEspecialidad(e.getId());
			dto.setNombreEspecialidad(e.getNombreEspecialidad());
			dtos.add(dto);
		}
		
		return dtos;
	}

}
