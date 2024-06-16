package equipo1obrasocial.dtos.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDTOResponse {

	private long idReceta;
	private String nombreClinica;
	private String direccionClinica;
	private String especialidadMedico;
	private LocalDate fecha;
	private String diagnostico;
	private String tratamiento;
	private String nombreCompletoMedico;
}
