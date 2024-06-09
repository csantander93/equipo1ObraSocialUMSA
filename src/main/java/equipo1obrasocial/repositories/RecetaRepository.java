package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Receta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecetaRepository implements PanacheRepository<Receta> {

	public Receta findById(long id) {
		return find("id",id).firstResult();
	}
	
	public Receta findByTurnoId(Long idTurno) {
        return find("turno.id", idTurno).firstResult();
    } 
}
