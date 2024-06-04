package equipo1obrasocial.services;

import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.repositories.ClinicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClinicaService implements IClinicaService {

	@Inject
	private ClinicaRepository clinicaRepository;
	
	@Override
	@Transactional
	public boolean crearClinica(String nombre, String direccion) throws Exception {
		
		if(clinicaRepository.findByNombre(nombre) != null) {
			
			throw new Exception("La clínica " + nombre + ", que estás intentando crear, ya está creada.");
		}
		
		if(clinicaRepository.findByDireccion(direccion) != null) {
			
			throw new Exception("Ya hay una clínica en la dirección " + direccion + ".");
		}
		
		Clinica clinica = new Clinica();
		
		clinica.setNombre(nombre);
		
		clinica.setDireccion(direccion);
		
		clinicaRepository.persist(clinica);
		
		return true;
	}

}
