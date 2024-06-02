package equipo1obrasocial.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column
	private String nombre;
	
	
	@Column
	private String apellido;
	
	
	@Column(unique = true)
	private String dni;
	
	
	@Column
	private String num_afiliado;
	
	
	@Column
	private LocalDate fecha_nac;
	
	@OneToMany(mappedBy = "paciente")
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "paciente")
	private List<Receta> recetas;
}
