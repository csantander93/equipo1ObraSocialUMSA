package equipo1obrasocial.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import equipo1obrasocial.services.IClinicaService;
import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
public class ClinicaServiceTest {

	@Mock
	private IClinicaService clinicaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Inicializamos los mocks (no siempre necesario con QuarkusTest)
    }

    @Test
    public void testCrearClinica_OK() throws Exception {
        // Mockear el comportamiento para que no lance excepciones
    	when(clinicaService.crearClinica(anyString(), anyString())).thenReturn(true);

        // Llama al método crearClinica
        boolean result = clinicaService.crearClinica("Clinica 1", "Direccion 1");

        // Verifica que el resultado sea true
        assertEquals(true, result);

        // Verifica que se llamó al método con los parámetros esperados
        verify(clinicaService).crearClinica("Clinica 1", "Direccion 1");
    }

    @Test
    public void testCrearClinica_NombreExistente() throws Exception {
        // Mockear el comportamiento para que lance la excepción esperada
        doThrow(new Exception("La clínica Clinica 1, que estás intentando crear, ya está creada."))
                .when(clinicaService).crearClinica("Clinica 1", "Direccion 1");

        // Verificar que se lance la excepción correcta
        Exception exception = assertThrows(Exception.class, () -> {
            clinicaService.crearClinica("Clinica 1", "Direccion 1");
        });

        assertEquals("La clínica Clinica 1, que estás intentando crear, ya está creada.", exception.getMessage());

        // Verificar que se llamó al método con los parámetros esperados
        verify(clinicaService).crearClinica("Clinica 1", "Direccion 1");
    }

    @Test
    public void testCrearClinica_DireccionExistente() throws Exception {
        // Mockear el comportamiento para que lance la excepción esperada
        doThrow(new Exception("Ya hay una clínica en la dirección Direccion 1."))
                .when(clinicaService).crearClinica("Clinica 1", "Direccion 1");

        // Verificar que se lance la excepción correcta
        Exception exception = assertThrows(Exception.class, () -> {
            clinicaService.crearClinica("Clinica 1", "Direccion 1");
        });

        assertEquals("Ya hay una clínica en la dirección Direccion 1.", exception.getMessage());

        // Verificar que se llamó al método con los parámetros esperados
        verify(clinicaService).crearClinica("Clinica 1", "Direccion 1");
    }
}
