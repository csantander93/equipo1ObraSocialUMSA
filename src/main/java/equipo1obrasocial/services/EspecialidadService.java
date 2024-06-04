package equipo1obrasocial.services;

import equipo1obrasocial.entities.Especialidad;
import equipo1obrasocial.repositories.EspecialidadRepository;
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

}
