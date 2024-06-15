package equipo1obrasocial.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private long id;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column(name="rol_usuario")
	private String rolUsuario;
	
	@OneToOne
	@JoinColumn(name = "id_medico")
	private Medico medico;
	
	@OneToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
}
