package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Paciente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository <Paciente>{
	
	public Paciente findById(long id) {
		return find("id", id).firstResult();
	}
	
	public Paciente findByDni(String dni) {
		return find("dni",dni).firstResult();
	}

}
