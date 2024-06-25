package equipo1obrasocial.repositories;

import java.util.List;

import equipo1obrasocial.entities.Especialidad;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EspecialidadRepository implements PanacheRepository<Especialidad> {

    public Especialidad findByNombre(String nombre) {
        return find("nombreEspecialidad", nombre).firstResult();
    }
    
    public List<Especialidad> findAllEspecialidades() {
        return listAll();
    }
}
