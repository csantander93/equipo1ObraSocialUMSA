package equipo1obrasocial.entities;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class Turno extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_turno")
	private long id;
	
	@Column
	private LocalDateTime fecha_hora;

	@ManyToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
	
	@Column
	private String motivoConsulta;
	
	@Column
	private boolean activo;
	
	@ManyToOne
	@JoinColumn(name = "id_medico")
	private Medico medico;
	
    @OneToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;

}
