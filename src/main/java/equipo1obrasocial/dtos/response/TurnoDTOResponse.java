package equipo1obrasocial.dtos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurnoDTOResponse {
	
	private long idTurno;
	private LocalDateTime fechaHora;
	private String motivoConsulta;

}
