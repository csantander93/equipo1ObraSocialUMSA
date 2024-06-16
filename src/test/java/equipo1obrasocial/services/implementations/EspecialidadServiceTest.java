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
        MockitoAnnotations.initMocks(this); 
    }

    @Test
    public void testCrearEspecialidad_OK() throws Exception {
    	
        when(especialidadService.crearEspecialidad(anyString())).thenReturn(true);

        boolean result = especialidadService.crearEspecialidad("Cardiologia");

        assertEquals(true, result);

        verify(especialidadService).crearEspecialidad("Cardiologia");
    }

    @Test
    public void testCrearEspecialidad_NombreExistente() throws Exception {
        // Mockear el comportamiento para que lance la excepción esperada
        doThrow(new Exception("Ya existe la especialidad Cardiologia"))
                .when(especialidadService).crearEspecialidad("Cardiología");

        // Verificar que se lance la excepción correcta
        Exception exception = assertThrows(Exception.class, () -> {
            especialidadService.crearEspecialidad("Cardiologia");
        });

        assertEquals("Ya existe la especialidad Cardiologia", exception.getMessage());

        verify(especialidadService).crearEspecialidad("Cardiologia");
    }




}
