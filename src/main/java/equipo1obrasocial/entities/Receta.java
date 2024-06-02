package equipo1obrasocial.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Receta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column
	private String clinica;
	
	@Column
	private LocalDate fecha;
	
	@Column
	private String diagnostico;
	
	@Column
	private String tratamiento;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "id_medico")
	private Medico medico;
}