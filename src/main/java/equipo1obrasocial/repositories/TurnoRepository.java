package equipo1obrasocial.repositories;

import java.util.List;

import equipo1obrasocial.entities.Turno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TurnoRepository implements PanacheRepository<Turno> {
	
    public Turno findById(long id) {
        return find("id", id).firstResult();
    }
    
    public List<Turno> findByMedicoIdAndActivo(long medicoId) {
        return list("medico.id = ?1 and activo = ?2", medicoId, true);
    }
}
