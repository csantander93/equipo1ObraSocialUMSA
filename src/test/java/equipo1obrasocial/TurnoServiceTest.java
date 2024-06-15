package equipo1obrasocial;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import equipo1obrasocial.dtos.request.TurnoDTOMedicoPaciente;
import equipo1.obrasocial.exceptions.TurnoOcupadoException;
import equipo1.obrasocial.exceptions.TurnoFueraDeHorarioException;
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
}
