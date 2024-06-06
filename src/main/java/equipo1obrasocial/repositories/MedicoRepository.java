package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Medico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MedicoRepository implements PanacheRepository<Medico> {
	
	public Medico findByMatricula(String matricula) {
		return find("matricula",matricula).firstResult();
	}
}
