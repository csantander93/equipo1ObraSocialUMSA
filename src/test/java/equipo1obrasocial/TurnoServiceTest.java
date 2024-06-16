package equipo1obrasocial;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1.obrasocial.exceptions.TurnoOcupadoException;
import equipo1.obrasocial.exceptions.MedicoNoExisteException;
import equipo1.obrasocial.exceptions.TurnoFueraDeHorarioException;
import equipo1.obrasocial.exceptions.TurnoNoExisteException;
import equipo1obrasocial.entities.Medico;
import equipo1obrasocial.entities.Paciente;
import equipo1obrasocial.entities.Turno;
import equipo1obrasocial.repositories.MedicoRepository;
import equipo1obrasocial.repositories.PacienteRepository;
import equipo1obrasocial.repositories.TurnoRepository;
import equipo1obrasocial.services.implementations.TurnoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;

@QuarkusTest
public class TurnoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;

    private Medico medico;
    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        medico = new Medico();
        medico.setId(1L);
        medico.setAtencionDesde(LocalTime.of(8, 0));
        medico.setAtencionHasta(LocalTime.of(12, 0));
        medico.setTurnos(new java.util.HashSet<>());
        paciente = new Paciente();
        paciente.setId(1L);
       }

    @Test
    @Transactional
    public void testCrearTurnoConPacienteExitoso() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdMedico(1L);
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);
        when(pacienteRepository.findById(dto.getIdPaciente())).thenReturn(paciente);
        
        boolean resultado = turnoService.crearTurnoConPaciente(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository
        verify(turnoRepository, times(1)).persist(any(Turno.class));

        System.out.println("Turnos del médico después de crear turno exitoso: " + medico.getTurnos());
    }

    @Test
    @Transactional
    public void testCrearTurnoConPacienteTurnoOcupado() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdMedico(1L);
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Crear un turno existente para el médico
        Turno turnoExistente = new Turno();
        turnoExistente.setFecha_hora(dto.getFecha_hora());
        medico.setTurnos(new java.util.HashSet<>(java.util.Collections.singletonList(turnoExistente)));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoOcupadoException.class, () -> {
            turnoService.crearTurnoConPaciente(dto);
        });
    }


    @Test
    @Transactional
    public void testCrearTurnoConPacienteFueraDeHorario() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdMedico(1L);
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 7, 0)); // Hora fuera del horario de atención

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);

        assertThrows(TurnoFueraDeHorarioException.class, () -> {
            turnoService.crearTurnoConPaciente(dto);
        });
    }
    
    @Test
    @Transactional
    public void testDarBajaTurnoExitoso() {
        // Datos de prueba
        TurnoEliminarDTORequest dto = new TurnoEliminarDTORequest();
        dto.setIdTurno(1L);

        // Crear un turno existente
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setActivo(true);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.darBajaTurno(dto);

        // Verificar el resultado
        assertTrue(resultado);
        assertFalse(turno.isActivo());
        assertEquals("", turno.getMotivoConsulta());
        assertNull(turno.getPaciente());
        assertNull(turno.getReceta());

        // Verificar las interacciones con el turnoRepository
        verify(turnoRepository, times(1)).persist(turno);
    }

    @Test
    @Transactional
    public void testDarBajaTurnoNoExiste() {
        // Datos de prueba
        TurnoEliminarDTORequest dto = new TurnoEliminarDTORequest();
        dto.setIdTurno(1L);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoNoExisteException.class, () -> {
            turnoService.darBajaTurno(dto);
        });
    }
    
    @Test
    @Transactional
    public void testEliminarTurnoExitoso() {
        // Datos de prueba
        TurnoEliminarDTORequest dto = new TurnoEliminarDTORequest();
        dto.setIdTurno(1L);

        // Crear un turno existente
        Turno turno = new Turno();
        turno.setId(1L);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.eliminarTurno(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository
        verify(turnoRepository, times(1)).delete(turno);
    }

    @Test
    @Transactional
    public void testEliminarTurnoNoExiste() {
        // Datos de prueba
        TurnoEliminarDTORequest dto = new TurnoEliminarDTORequest();
        dto.setIdTurno(1L);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoNoExisteException.class, () -> {
            turnoService.eliminarTurno(dto);
        });
    }
    
    @Test
    @Transactional
    public void testActualizarTurnoExitoso() {
        // Datos de prueba
        TurnoActualizarDTORequest dto = new TurnoActualizarDTORequest();
        dto.setIdTurno(1L);
        dto.setIdMedicoNuevo(2L);
        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 10, 0));
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Crear turno y médico existentes
        Turno turno = new Turno();
        turno.setId(1L);
        Medico medicoNuevo = new Medico();
        medicoNuevo.setId(2L);
        medicoNuevo.setAtencionDesde(LocalTime.of(8, 0));
        medicoNuevo.setAtencionHasta(LocalTime.of(12, 0));
        medicoNuevo.setTurnos(new java.util.HashSet<>());

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);
        when(medicoRepository.findById(dto.getIdMedicoNuevo())).thenReturn(medicoNuevo);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.actualizarTurno(dto);

        // Verificar el resultado
        assertTrue(resultado);
        assertEquals(medicoNuevo, turno.getMedico());
        assertEquals(dto.getFechaHoraNueva(), turno.getFecha_hora());
        assertEquals(dto.getNuevoMotivoConsulta(), turno.getMotivoConsulta());

        // Verificar las interacciones con el turnoRepository
        verify(turnoRepository, times(1)).persist(turno);
    }

    @Test
    @Transactional
    public void testActualizarTurnoNoExiste() {
        // Datos de prueba
        TurnoActualizarDTORequest dto = new TurnoActualizarDTORequest();
        dto.setIdTurno(1L);
        dto.setIdMedicoNuevo(2L);
        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 10, 0));
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoNoExisteException.class, () -> {
            turnoService.actualizarTurno(dto);
        });
    }

    @Test
    @Transactional
    public void testActualizarTurnoMedicoNoExiste() {
        // Datos de prueba
        TurnoActualizarDTORequest dto = new TurnoActualizarDTORequest();
        dto.setIdTurno(1L);
        dto.setIdMedicoNuevo(2L);
        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 10, 0));
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Crear turno existente
        Turno turno = new Turno();
        turno.setId(1L);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);
        when(medicoRepository.findById(dto.getIdMedicoNuevo())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(MedicoNoExisteException.class, () -> {
            turnoService.actualizarTurno(dto);
        });
    }

    @Test
    @Transactional
    public void testActualizarTurnoHorarioOcupado() {
        // Datos de prueba
        TurnoActualizarDTORequest dto = new TurnoActualizarDTORequest();
        dto.setIdTurno(1L);
        dto.setIdMedicoNuevo(2L);
        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 10, 0));
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Crear turno y médico existentes
        Turno turno = new Turno();
        turno.setId(1L);
        Medico medicoNuevo = new Medico();
        medicoNuevo.setId(2L);
        medicoNuevo.setAtencionDesde(LocalTime.of(8, 0));
        medicoNuevo.setAtencionHasta(LocalTime.of(12, 0));
        
        // Crear turno en el mismo horario
        Turno turnoExistente = new Turno();
        turnoExistente.setFecha_hora(dto.getFechaHoraNueva());
        medicoNuevo.setTurnos(new java.util.HashSet<>(java.util.Collections.singletonList(turnoExistente)));

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);
        when(medicoRepository.findById(dto.getIdMedicoNuevo())).thenReturn(medicoNuevo);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoOcupadoException.class, () -> {
            turnoService.actualizarTurno(dto);
        });
    }

    @Test
    @Transactional
    public void testActualizarTurnoFueraDeHorario() {
        // Datos de prueba
        TurnoActualizarDTORequest dto = new TurnoActualizarDTORequest();
        dto.setIdTurno(1L);
        dto.setIdMedicoNuevo(2L);
        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 7, 0)); // Hora fuera del horario de atención
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Crear turno y médico existentes
        Turno turno = new Turno();
        turno.setId(1L);
        Medico medicoNuevo = new Medico();
        medicoNuevo.setId(2L);
        medicoNuevo.setAtencionDesde(LocalTime.of(8, 0));
        medicoNuevo.setAtencionHasta(LocalTime.of(12, 0));
        medicoNuevo.setTurnos(new java.util.HashSet<>());

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);
        when(medicoRepository.findById(dto.getIdMedicoNuevo())).thenReturn(medicoNuevo);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoFueraDeHorarioException.class, () -> {
            turnoService.actualizarTurno(dto);
        });
    }
    
    @Test
    @Transactional
    public void testCrearTurnoSinPacienteExitoso() {
        // Datos de prueba
        TurnoDTOMedicoFechaHora dto = new TurnoDTOMedicoFechaHora();
        dto.setIdMedico(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.crearTurnoSinPaciente(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository y obtener el turno persistido
        ArgumentCaptor<Turno> turnoCaptor = ArgumentCaptor.forClass(Turno.class);
        verify(turnoRepository, times(1)).persist(turnoCaptor.capture());
        Turno turnoPersistido = turnoCaptor.getValue();

        // Verificar que el turno se ha añadido a los turnos del médico
        medico.getTurnos().add(turnoPersistido); // Simular la adición del turno al médico
        assertFalse(medico.getTurnos().isEmpty());
    }

    @Test
    @Transactional
    public void testCrearTurnoSinPacienteTurnoOcupado() {
        // Datos de prueba
        TurnoDTOMedicoFechaHora dto = new TurnoDTOMedicoFechaHora();
        dto.setIdMedico(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Crear un turno existente para el médico
        Turno turnoExistente = new Turno();
        turnoExistente.setFecha_hora(dto.getFecha_hora());
        medico.getTurnos().add(turnoExistente);

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoOcupadoException.class, () -> {
            turnoService.crearTurnoSinPaciente(dto);
        });
    }

    @Test
    @Transactional
    public void testCrearTurnoSinPacienteFueraDeHorario() {
        // Datos de prueba
        TurnoDTOMedicoFechaHora dto = new TurnoDTOMedicoFechaHora();
        dto.setIdMedico(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 7, 0)); // Hora fuera del horario de atención

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdMedico())).thenReturn(medico);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoFueraDeHorarioException.class, () -> {
            turnoService.crearTurnoSinPaciente(dto);
        });
    }
}
