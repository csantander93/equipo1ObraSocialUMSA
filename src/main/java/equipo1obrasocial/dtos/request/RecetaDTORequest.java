package equipo1obrasocial.dtos.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDTORequest {

	private String diagnostico;
	private LocalDate fecha;
	private String tratamiento;
	private long idMedico;
	private long idPaciente;
	private long idTurno;
}
