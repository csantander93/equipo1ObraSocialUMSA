package equipo1obrasocial.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTORequest {

	private String email;
	private String password;
	private String rolUsuario;
	private String identificador;
}
