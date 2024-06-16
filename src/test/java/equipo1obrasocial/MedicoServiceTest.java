package equipo1obrasocial;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import equipo1obrasocial.dtos.response.MedicoDTOResponse;
import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.entities.Especialidad;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.repositories.ClinicaRepository;
import equipo1obrasocial.repositories.EspecialidadRepository;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.services.implementations.MedicoService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EspecialidadRepository especialidadRepository;

    @Mock
    private ClinicaRepository clinicaRepository;

    @InjectMocks
    private MedicoService medicoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCartillaConMedicos() throws Exception {
        // Crear lista de médicos
        List<Medico> medicos = new ArrayList<>();
        
        // Crear una especialidad y asignarla a los médicos
        Especialidad especialidad = new Especialidad();
        especialidad.setNombreEspecialidad("Cardiología");

        // Crear una clínica y asignarla a los médicos
        Clinica clinica = new Clinica();
        clinica.setNombre("AlMedin");
        clinica.setDireccion("Av. Siempre Viva 123");
        
        // Crear el primer médico y asignar nombre, apellido, especialidad y clínica
        Medico medico1 = new Medico();
        medico1.setNombre("Juan");
        medico1.setApellido("Perez");
        medico1.setEspecialidad(especialidad);
        medico1.setClinica(clinica);

        // Crear el segundo médico y asignar nombre, apellido, especialidad y clínica
        Medico medico2 = new Medico();
        medico2.setNombre("Ana");
        medico2.setApellido("Garcia");
        medico2.setEspecialidad(especialidad);
        medico2.setClinica(clinica);

        // Añadir los médicos a la lista de médicos
        medicos.add(medico1);
        medicos.add(medico2);

        // Crear un mock de PanacheQuery y configurar su comportamiento
        PanacheQuery<Medico> panacheQuery = mock(PanacheQuery.class);
        when(panacheQuery.list()).thenReturn(medicos);
        // Configurar el mock de MedicoRepository para que devuelva el mock de PanacheQuery
        when(medicoRepository.findAll()).thenReturn(panacheQuery);

        // Llamar al método getCartilla del servicio y obtener la lista de MedicoDTOResponse
        List<MedicoDTOResponse> cartilla = medicoService.getCartilla();

        // Verificar que la lista no es nula
        assertNotNull(cartilla);
        // Verificar que la lista contiene exactamente 2 elementos
        assertEquals(2, cartilla.size());

        // Verificar los datos del primer médico en la lista
        assertEquals("Juan Perez", cartilla.get(0).getNombreMedico());
        assertEquals("Cardiología", cartilla.get(0).getNombreEspecialidad());
        // Verificar los datos del segundo médico en la lista
        assertEquals("Ana Garcia", cartilla.get(1).getNombreMedico());
        assertEquals("Cardiología", cartilla.get(1).getNombreEspecialidad());
    }

    @Test
    public void testGetCartillaSinMedicos() {
    	
        PanacheQuery<Medico> panacheQuery = mock(PanacheQuery.class);
        when(panacheQuery.list()).thenReturn(new ArrayList<>());
        when(medicoRepository.findAll()).thenReturn(panacheQuery);

        Exception exception = assertThrows(Exception.class, () -> {
            medicoService.getCartilla();
        });

        String expectedMessage = "No hay médicos que mostrar.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
