package equipo1obrasocial.repositories;

import equipo1obrasocial.entities.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario>{

	public Usuario findByEmail(String email) {
		return find("email",email).firstResult();
	}
	
	public Usuario findByPacienteId(Long pacienteId) {
	    return find("paciente.id", pacienteId).firstResult();
	}
	
	public Usuario findByMedicoId(Long medicoId) {
	    return find("medico.id", medicoId).firstResult();
	}
}
