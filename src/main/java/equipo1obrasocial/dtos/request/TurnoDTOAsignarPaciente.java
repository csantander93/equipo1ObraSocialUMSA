package equipo1obrasocial.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTOAsignarPaciente {

	private long idTurno;
	private long idUsuario;
	private String motivoConsulta;
	
}
