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

import equipo1obrasocial.services.IEspecialidadService;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EspecialidadServiceTest {

    @Mock
    private IEspecialidadService especialidadService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
    }

    @Test
    public void testCrearEspecialidad_OK() throws Exception {
        // Mockear el comportamiento para que no lance excepciones
        when(especialidadService.crearEspecialidad(anyString())).thenReturn(true);

        boolean result = especialidadService.crearEspecialidad("Cardiologia");

        assertEquals(true, result);

        // Verifica que se llamo al metodo con los parametros esperados
        verify(especialidadService).crearEspecialidad("Cardiologia");
    }

    @Test
    public void testCrearEspecialidad_NombreExistente() throws Exception {
        // Mockear el comportamiento para que lance la excepcion esperada
        doThrow(new Exception("Ya existe la especialidad Cardiologia"))
                .when(especialidadService).crearEspecialidad("Cardiologia");

        // Verificar que se lance la excepcion correcta
        Exception exception = assertThrows(Exception.class, () -> {
            especialidadService.crearEspecialidad("Cardiologia");
        });

        assertEquals("Ya existe la especialidad Cardiologia", exception.getMessage());

        // Verifica que se llamo al metodo con los parametros esperados
        verify(especialidadService).crearEspecialidad("Cardiologia");
    }
}
