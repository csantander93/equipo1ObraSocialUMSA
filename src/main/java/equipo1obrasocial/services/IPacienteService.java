package equipo1obrasocial.services;

import java.time.LocalDate;

public interface IPacienteService {

	public boolean crearPaciente(String nombre, String apellido, String dni, String num_afiliado, LocalDate fecha_nac) throws Exception;
}
