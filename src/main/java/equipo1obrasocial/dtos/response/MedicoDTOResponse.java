package equipo1obrasocial.dtos.response;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTOResponse {

	private String nombreMedico;
	
	private String nombreEspecialidad;
	
	private LocalTime atencionDesde;
	
	private LocalTime atencionHasta;
	
	private String ubicacionConsulta;
}
