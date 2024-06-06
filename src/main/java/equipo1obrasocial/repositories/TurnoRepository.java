package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Turno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TurnoRepository implements PanacheRepository<Turno> {
	
    public Turno findById(long id) {
        return find("id_turno", id).firstResult();
    }

}
