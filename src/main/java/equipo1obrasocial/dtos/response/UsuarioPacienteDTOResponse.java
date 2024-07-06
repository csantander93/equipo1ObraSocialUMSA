package equipo1obrasocial.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPacienteDTOResponse {
	
	private long idUsuario;
	private long idPaciente;
	private String nombreCompleto;
	private String dni;
	private String numAfiliado;

}
