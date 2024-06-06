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
public class TurnoActualizarDTORequest {
	
	private long idTurno;
	private LocalDateTime fechaHoraNueva;
	private long idMedicoNuevo;
	private String nuevoMotivoConsulta;

}
