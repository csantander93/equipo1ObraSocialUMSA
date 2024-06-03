package equipo1obrasocial.entities;

import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

public class Medico extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_medico")
	private long id;
	
	@Column
	private String nombre;
	
	@Column
	private String apellido;
	
	@Column
	private String matricula;
	
	@OneToMany(mappedBy = "medico")
	private Set<Turno> turnos;
	
	@OneToMany(mappedBy = "medico")
	private Set<Receta> recetas;
	
	@ManyToOne
	@JoinColumn(name = "id_especialidad") 
	private Especialidad especialidad;
	
	@ManyToOne
	@JoinColumn(name = "id_clinica")
	private Clinica clinica;
}
