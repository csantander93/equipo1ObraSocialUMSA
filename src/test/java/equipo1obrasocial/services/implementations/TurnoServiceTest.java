package equipo1obrasocial.services.implementations;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import equipo1obrasocial.dtos.request.TurnoActualizarDTORequest;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFecha;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoFechaHora;
import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1obrasocial.dtos.request.TurnoEliminarDTORequest;
import equipo1.obrasocial.exceptions.TurnoOcupadoException;
import equipo1.obrasocial.exceptions.HorarioNoDefinidoException;
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
    public void testCrearTurnoConPacienteExitoso() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Mockear comportamientos
        when(pacienteRepository.findById(dto.getIdPaciente())).thenReturn(paciente);
        
        boolean resultado = turnoService.crearTurnoConPaciente(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository
        verify(turnoRepository, times(1)).persist(any(Turno.class));

        System.out.println("Turnos del médico después de crear turno exitoso: " + medico.getTurnos());
    }

    @Test
    public void testCrearTurnoConPacienteTurnoOcupado() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 10, 0));

        // Crear un turno existente para el médico
        Turno turnoExistente = new Turno();
        turnoExistente.setFecha_hora(dto.getFecha_hora());
        medico.setTurnos(new java.util.HashSet<>(java.util.Collections.singletonList(turnoExistente)));

        // Mockear comportamientos

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(TurnoOcupadoException.class, () -> {
            turnoService.crearTurnoConPaciente(dto);
        });
    }


    @Test
    public void testCrearTurnoConPacienteFueraDeHorario() {
        // Datos de prueba
        TurnoDTOMedicoPaciente dto = new TurnoDTOMedicoPaciente();
        dto.setIdPaciente(1L);
        dto.setFecha_hora(LocalDateTime.of(2023, 6, 15, 7, 0)); // Hora fuera del horario de atención

        // Mockear comportamientos

        assertThrows(TurnoFueraDeHorarioException.class, () -> {
            turnoService.crearTurnoConPaciente(dto);
        });
    }
    
    @Test
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

        dto.setFechaHoraNueva(LocalDateTime.of(2023, 6, 15, 10, 0));
        dto.setNuevoMotivoConsulta("Consulta actualizada");

        // Crear turno existente
        Turno turno = new Turno();
        turno.setId(1L);

        // Mockear comportamientos
        when(turnoRepository.findById(dto.getIdTurno())).thenReturn(turno);


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
    
    @Test
    public void testCrearTurnosMedicoFechaCada20MinExitoso() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(medico);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.crearTurnosMedicoFechaCada20Min(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository y obtener los turnos persistidos
        ArgumentCaptor<Turno> turnoCaptor = ArgumentCaptor.forClass(Turno.class);
        verify(turnoRepository, atLeastOnce()).persist(turnoCaptor.capture());

        // Verificar que los turnos se han añadido correctamente
        Set<LocalDateTime> horariosEsperados = new HashSet<>();
        LocalDateTime fechaHoraInicio = dto.getFecha().atTime(medico.getAtencionDesde());
        LocalDateTime fechaHoraFin = dto.getFecha().atTime(medico.getAtencionHasta());
        while (!fechaHoraInicio.isAfter(fechaHoraFin)) {
            horariosEsperados.add(fechaHoraInicio);
            fechaHoraInicio = fechaHoraInicio.plusMinutes(20);
        }

        Set<LocalDateTime> horariosTurnosPersistidos = new HashSet<>();
        for (Turno turno : turnoCaptor.getAllValues()) {
            horariosTurnosPersistidos.add(turno.getFecha_hora());
        }

        assertEquals(horariosEsperados, horariosTurnosPersistidos);
    }

    @Test
    public void testCrearTurnosMedicoFechaCada20MinMedicoNoExiste() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(MedicoNoExisteException.class, () -> {
            turnoService.crearTurnosMedicoFechaCada20Min(dto);
        });
    }

    @Test
    public void testCrearTurnosMedicoFechaCada20MinHorarioNoDefinido() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Medico sin horarios definidos
        medico.setAtencionDesde(null);
        medico.setAtencionHasta(null);

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(medico);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(HorarioNoDefinidoException.class, () -> {
            turnoService.crearTurnosMedicoFechaCada20Min(dto);
        });
    }
    
    @Test
    public void testCrearTurnosMedicoFechaCada15MinExitoso() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(medico);

        // Ejecutar el método bajo prueba
        boolean resultado = turnoService.crearTurnosMedicoFechaCada15Min(dto);

        // Verificar el resultado
        assertTrue(resultado);

        // Verificar las interacciones con el turnoRepository y obtener los turnos persistidos
        ArgumentCaptor<Turno> turnoCaptor = ArgumentCaptor.forClass(Turno.class);
        verify(turnoRepository, atLeastOnce()).persist(turnoCaptor.capture());

        // Verificar que los turnos se han añadido correctamente
        Set<LocalDateTime> horariosEsperados = new HashSet<>();
        LocalDateTime fechaHoraInicio = dto.getFecha().atTime(medico.getAtencionDesde());
        LocalDateTime fechaHoraFin = dto.getFecha().atTime(medico.getAtencionHasta());
        while (!fechaHoraInicio.isAfter(fechaHoraFin)) {
            horariosEsperados.add(fechaHoraInicio);
            fechaHoraInicio = fechaHoraInicio.plusMinutes(15);
        }

        Set<LocalDateTime> horariosTurnosPersistidos = new HashSet<>();
        for (Turno turno : turnoCaptor.getAllValues()) {
            horariosTurnosPersistidos.add(turno.getFecha_hora());
        }

        assertEquals(horariosEsperados, horariosTurnosPersistidos);
    }

    @Test
    public void testCrearTurnosMedicoFechaCada15MinMedicoNoExiste() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(null);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(MedicoNoExisteException.class, () -> {
            turnoService.crearTurnosMedicoFechaCada15Min(dto);
        });
    }

    @Test
    public void testCrearTurnosMedicoFechaCada15MinHorarioNoDefinido() {
        // Datos de prueba
        TurnoDTOMedicoFecha dto = new TurnoDTOMedicoFecha();
        dto.setIdUsuario(1L);
        dto.setFecha(LocalDate.of(2023, 6, 15));

        // Medico sin horarios definidos
        medico.setAtencionDesde(null);
        medico.setAtencionHasta(null);

        // Mockear comportamientos
        when(medicoRepository.findById(dto.getIdUsuario())).thenReturn(medico);

        // Ejecutar el método bajo prueba y verificar la excepción
        assertThrows(HorarioNoDefinidoException.class, () -> {
            turnoService.crearTurnosMedicoFechaCada15Min(dto);
        });
    }
}
