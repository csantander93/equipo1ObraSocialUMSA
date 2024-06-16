package equipo1obrasocial.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import equipo1obrasocial.dtos.response.RecetaDTOResponse;
import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Receta;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.RecetaRepository;
import equipo1obrasocial.repositories.TurnoRepository;
import equipo1obrasocial.services.implementations.RecetaService;

import java.time.LocalDate;

public class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private RecetaService recetaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTraerRecetaPorTurno() throws Exception {
        // Configurar los datos de prueba
        long idTurno = 1L;

        Clinica clinica = new Clinica();
        clinica.setNombre("AlMedin");
        clinica.setDireccion("123 Calle Falsa");

        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Juan");
        medico.setApellido("Perez");
        medico.setClinica(clinica);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Ana");
        paciente.setApellido("Garcia");

        Turno turno = new Turno();
        turno.setId(idTurno);
        turno.setMedico(medico);
        turno.setPaciente(paciente);

        Receta receta = new Receta();
        receta.setId(1L);
        receta.setMedico(medico);
        receta.setPaciente(paciente);
        receta.setTurno(turno);
        receta.setFecha(LocalDate.now());
        receta.setDiagnostico("Gripe");
        receta.setTratamiento("Tomar 1 paracetamol cada 2 horas");

        // Configurar el mock del repositorio
        when(recetaRepository.findByTurnoId(idTurno)).thenReturn(receta);

        // Llamar al método bajo prueba
        RecetaDTOResponse respuesta = recetaService.traerRecetaPorTurno(idTurno);

        // Verificar los resultados
        assertNotNull(respuesta);
        assertEquals(receta.getId(), respuesta.getIdReceta());
        assertEquals(clinica.getNombre(), respuesta.getNombreClinica());
        assertEquals(clinica.getDireccion(), respuesta.getDireccionClinica());
        assertEquals(receta.getFecha(), respuesta.getFecha());
        assertEquals(receta.getDiagnostico(), respuesta.getDiagnostico());
        assertEquals(receta.getTratamiento(), respuesta.getTratamiento());
        assertEquals(medico.getNombre() + " " + medico.getApellido(), respuesta.getNombreCompletoMedico());

        // Verificar que se llamó al método del repositorio con el ID correcto
        verify(recetaRepository, times(1)).findByTurnoId(idTurno);
    }
}
