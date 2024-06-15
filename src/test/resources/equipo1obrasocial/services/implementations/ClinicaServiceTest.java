package equipo1obrasocial.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import equipo1obrasocial.entities.Clinica;
import equipo1obrasocial.repositories.ClinicaRepository;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClinicaServiceTest {

    @Mock
    private ClinicaRepository clinicaRepository;

    @InjectMocks
    private ClinicaService clinicaService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Ejecucion test clinicaService");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("Fin de ejecucion test clinicaService");
    }

    @Test
    public void testCrearClinica_Correcta() throws Exception {

        String nombre = "Clinica Godoy Cruz";
        String direccion = "Calle 123";

        //no existe ninguna clinica con el mismo nombre o direccion
        when(clinicaRepository.findByNombre(nombre)).thenReturn(null);
        when(clinicaRepository.findByDireccion(direccion)).thenReturn(null);

        boolean resultado = clinicaService.crearClinica(nombre, direccion);

        assertTrue(resultado);
        verify(clinicaRepository, times(1)).persist(any(Clinica.class));
    }

    @Test
    public void testCrearClinica_Nombre() {

        String nombre = "Clinica Godoy Cruz";
        String direccion = "Calle 123";
        
        // existe una clinica con el mismo nombre
        when(clinicaRepository.findByNombre(nombre)).thenReturn(new Clinica());

        Exception exception = assertThrows(Exception.class, () -> {
            clinicaService.crearClinica(nombre, direccion);
        });

        assertEquals("La clinica " + nombre + ", que estás intentando crear, ya está creada.", exception.getMessage());
        verify(clinicaRepository, never()).persist(any(Clinica.class));
    }

    @Test
    public void testCrearClinica_Direccion() {
    	
        String nombre = "Nueva Clinica";
        String direccion = "Calle 123";

        //existe una clinica con la misma direccion
        when(clinicaRepository.findByNombre(nombre)).thenReturn(null);
        when(clinicaRepository.findByDireccion(direccion)).thenReturn(new Clinica());

        Exception exception = assertThrows(Exception.class, () -> {
            clinicaService.crearClinica(nombre, direccion);
        });

        assertEquals("Ya hay una clinica en la direccion " + direccion + ", exception.getMessage());
        verify(clinicaRepository, never()).persist(any(Clinica.class));
    }
}
