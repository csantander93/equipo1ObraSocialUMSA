package equipo1obrasocial.entities;

import java.time.LocalDate;
import java.util.List;

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

public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column
	private String nombre;
	
	@Column
	private String apellido;
	
	@Column
	private String matricula;
	
	@OneToMany(mappedBy = "medico")
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "medico")
	private List<Receta> recetas;
	
	@ManyToOne
	@JoinColumn(name = "id_especialidad") 
	private Especialidad especialidad;
	
	@ManyToOne
	@JoinColumn(name = "id_clinica")
	private Clinica clinica;
}
