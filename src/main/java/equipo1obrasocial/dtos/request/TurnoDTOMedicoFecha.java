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
public class TurnoDTOMedicoFecha {
	
	private long idUsuario;
	private LocalDate fecha;

}
