package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Clinica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClinicaRepository implements PanacheRepository<Clinica> {

	public Clinica findByNombre(String nombre) {
		return find("nombre",nombre).firstResult();
	}
	
	public Clinica findByDireccion(String direccion) {
		return find("direccion",direccion).firstResult();
	}
}
