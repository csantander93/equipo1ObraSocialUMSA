package equipo1obrasocial.dtos.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTOMedicoPaciente {
	
	private long idUsuario;
	private long idPaciente;
	private LocalDateTime fecha_hora;
	private String motivoConsulta;

}
