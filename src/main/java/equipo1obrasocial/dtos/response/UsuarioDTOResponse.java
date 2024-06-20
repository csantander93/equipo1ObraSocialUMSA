package equipo1obrasocial.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTOResponse {
	
    private long id;
    private String email;
	private String rolUsuario;
	private String token;

}
