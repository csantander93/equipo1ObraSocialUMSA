-- create database almedin;
use almedin;
-- Insertar datos en la tabla Clinicas
INSERT INTO clinica (id_clinica, direccion, nombre)
VALUES (1, 'Larrea 949, Barrio Norte, CABA', 'Clinica Almedin');

-- Insertar datos en la tabla Especialidad
INSERT INTO especialidad (id_especialidad, nombreEspecialidad) VALUES
(1,'Cardiología'),
(2,'Pediatría'),
(3,'Neurología'),
(4,'Dermatología'),
(5,'Ginecología'),
(6,'Oftalmología'),
(7,'Psiquiatría'),
(8,'Ortopedia'),
(9,'Clínico'),
(10,'Obstetricia');

-- Insertar datos en la tabla Medico
INSERT INTO medico (id_medico, nombre, apellido, matricula, atencionDesde, atencionHasta, id_especialidad, id_clinica) VALUES
(1,'Juan', 'Pérez', '12345', '08:00:00', '16:00:00', 1, 1),
(2,'María', 'González', '67890', '09:00:00', '17:00:00', 2, 1),
(3,'Carlos', 'López', '11223', '10:00:00', '18:00:00', 3, 1),
(4,'Ana', 'Martínez', '33445', '07:00:00', '15:00:00', 4, 1),
(5,'Luis', 'Ramírez', '55667', '12:00:00', '20:00:00', 5, 1),
(6,'Elena', 'Sánchez', '77889', '14:00:00', '22:00:00', 6, 1),
(7,'Roberto', 'Fernández', '99000', '08:00:00', '16:00:00', 7, 1),
(8,'Patricia', 'Gómez', '22334', '10:00:00', '18:00:00', 8, 1),
(9,'Fernando', 'Hernández', '44556', '09:00:00', '17:00:00', 9, 1),
(10,'Gabriela', 'Torres', '66778', '11:00:00', '19:00:00', 10, 1);

-- Insertar datos en la tabla Pacientes
INSERT INTO paciente (id_paciente, apellido, dni, fecha_nac, nombre, num_afiliado) VALUES
(1, 'apellidoUsuario', '12345678', '1980-01-01', 'usuario1', 'A123456'), 
(2, 'Pérez', '23456789', '1985-02-15', 'María', 'B234567'),
(3, 'López', '34567890', '1990-03-20', 'Carlos', 'C345678'),
(4, 'Fernández', '45678901', '1975-04-10', 'Ana', 'D456789'),
(5, 'Martínez', '56789012', '2000-05-25', 'Luis', 'E567890'),
(6, 'González', '67890123', '1995-06-30', 'Elena', 'F678901'),
(7, 'Rodríguez', '78901234', '1988-07-05', 'José', 'G789012'),
(8, 'Sánchez', '89012345', '1978-08-15', 'Laura', 'H890123'),
(9, 'Ramírez', '90123456', '1965-09-25', 'Miguel', 'I901234'),
(10, 'Torres', '01234567', '1992-10-10', 'Patricia', 'J012345');

-- Insertar datos en la tabla Turnos
INSERT INTO turno (id_turno, activo, fecha_hora, motivoConsulta, id_medico, id_paciente) VALUES
(1, true, '2024-07-01 08:00:00', 'Dolor fuerte en el pecho cuando camino bastante', 1, 1),
(2, false, '2024-07-02 09:00:00', NULL, 1, NULL),
(3, false, '2024-07-03 10:00:00', NULL, 1, NULL),
(4, false, '2024-07-04 11:00:00', NULL, 1, NULL),
(5, false, '2024-07-05 12:00:00', NULL, 1, NULL),
(6, false, '2024-07-06 13:00:00', NULL, 1, NULL),
(7, false, '2024-07-07 14:00:00', NULL, 1, NULL),
(8, false, '2024-07-08 15:00:00', NULL, 1, NULL),
(9, false, '2024-07-09 16:00:00', NULL, 1, NULL),
(10, false, '2024-07-10 17:00:00', NULL, 1, NULL),
(11, true, '2024-07-01 09:00:00', 'Mucho dolor de garganta', 2, 1),
(12, false, '2024-07-02 10:00:00', NULL, 2, NULL),
(13, false, '2024-07-03 11:00:00', NULL, 2, NULL),
(14, false, '2024-07-04 12:00:00', NULL, 2, NULL),
(15, false, '2024-07-05 13:00:00', NULL, 2, NULL),
(16, false, '2024-07-06 14:00:00', NULL, 2, NULL),
(17, false, '2024-07-07 15:00:00', NULL, 2, NULL),
(18, false, '2024-07-08 16:00:00', NULL, 2, NULL),
(19, false, '2024-07-09 17:00:00', NULL, 2, NULL),
(20, false, '2024-07-10 18:00:00', NULL, 2, NULL),
(21, false, '2024-07-01 10:00:00', NULL, 3, NULL),
(22, false, '2024-07-02 11:00:00', NULL, 3, NULL),
(23, false, '2024-07-03 12:00:00', NULL, 3, NULL),
(24, false, '2024-07-04 13:00:00', NULL, 3, NULL),
(25, false, '2024-07-05 14:00:00', NULL, 3, NULL),
(26, false, '2024-07-06 15:00:00', NULL, 3, NULL),
(27, false, '2024-07-07 16:00:00', NULL, 3, NULL),
(28, false, '2024-07-08 17:00:00', NULL, 3, NULL),
(29, false, '2024-07-09 18:00:00', NULL, 3, NULL),
(30, false, '2024-07-10 19:00:00', NULL, 3, NULL),
(31, false, '2024-07-01 07:00:00', NULL, 4, NULL),
(32, false, '2024-07-02 08:00:00', NULL, 4, NULL),
(33, false, '2024-07-03 09:00:00', NULL, 4, NULL),
(34, false, '2024-07-04 10:00:00', NULL, 4, NULL),
(35, false, '2024-07-05 11:00:00', NULL, 4, NULL),
(36, false, '2024-07-06 12:00:00', NULL, 4, NULL),
(37, false, '2024-07-07 13:00:00', NULL, 4, NULL),
(38, false, '2024-07-08 14:00:00', NULL, 4, NULL),
(39, false, '2024-07-09 15:00:00', NULL, 4, NULL),
(40, false, '2024-07-10 16:00:00', NULL, 4, NULL),
(41, false, '2024-07-01 12:00:00', NULL, 5, NULL),
(42, false, '2024-07-02 13:00:00', NULL, 5, NULL),
(43, false, '2024-07-03 14:00:00', NULL, 5, NULL),
(44, false, '2024-07-04 15:00:00', NULL, 5, NULL),
(45, false, '2024-07-05 16:00:00', NULL, 5, NULL),
(46, false, '2024-07-06 17:00:00', NULL, 5, NULL),
(47, false, '2024-07-07 18:00:00', NULL, 5, NULL),
(48, false, '2024-07-08 19:00:00', NULL, 5, NULL),
(49, false, '2024-07-09 20:00:00', NULL, 5, NULL),
(50, false, '2024-07-10 21:00:00', NULL, 5, NULL),
(51, false, '2024-07-01 14:00:00', NULL, 6, NULL),
(52, false, '2024-07-02 15:00:00', NULL, 6, NULL),
(53, false, '2024-07-03 16:00:00', NULL, 6, NULL),
(54, false, '2024-07-04 17:00:00', NULL, 6, NULL),
(55, false, '2024-07-05 18:00:00', NULL, 6, NULL),
(56, false, '2024-07-06 19:00:00', NULL, 6, NULL),
(57, false, '2024-07-07 20:00:00', NULL, 6, NULL),
(58, false, '2024-07-08 21:00:00', NULL, 6, NULL),
(59, false, '2024-07-09 22:00:00', NULL, 6, NULL),
(60, false, '2024-07-10 23:00:00', NULL, 6, NULL),
(61, false, '2024-07-01 08:00:00', NULL, 7, NULL),
(62, false, '2024-07-02 09:00:00', NULL, 7, NULL),
(63, false, '2024-07-03 10:00:00', NULL, 7, NULL),
(64, false, '2024-07-04 11:00:00', NULL, 7, NULL),
(65, false, '2024-07-05 12:00:00', NULL, 7, NULL),
(66, false, '2024-07-06 13:00:00', NULL, 7, NULL),
(67, false, '2024-07-07 14:00:00', NULL, 7, NULL),
(68, false, '2024-07-08 15:00:00', NULL, 7, NULL),
(69, false, '2024-07-09 16:00:00', NULL, 7, NULL),
(70, false, '2024-07-10 17:00:00', NULL, 7, NULL),
(71, false, '2024-07-01 10:00:00', NULL, 8, NULL),
(72, false, '2024-07-02 11:00:00', NULL, 8, NULL),
(73, false, '2024-07-03 12:00:00', NULL, 8, NULL),
(74, false, '2024-07-04 13:00:00', NULL, 8, NULL),
(75, false, '2024-07-05 14:00:00', NULL, 8, NULL),
(76, false, '2024-07-06 15:00:00', NULL, 8, NULL),
(77, false, '2024-07-07 16:00:00', NULL, 8, NULL),
(78, false, '2024-07-08 17:00:00', NULL, 8, NULL),
(79, false, '2024-07-09 18:00:00', NULL, 8, NULL),
(80, false, '2024-07-10 19:00:00', NULL, 8, NULL),
(81, false, '2024-07-01 09:00:00', NULL, 9, NULL),
(82, false, '2024-07-02 10:00:00', NULL, 9, NULL),
(83, false, '2024-07-03 11:00:00', NULL, 9, NULL),
(84, false, '2024-07-04 12:00:00', NULL, 9, NULL),
(85, false, '2024-07-05 13:00:00', NULL, 9, NULL),
(86, false, '2024-07-06 14:00:00', NULL, 9, NULL),
(87, false, '2024-07-07 15:00:00', NULL, 9, NULL),
(88, false, '2024-07-08 16:00:00', NULL, 9, NULL),
(89, false, '2024-07-09 17:00:00', NULL, 9, NULL),
(90, false, '2024-07-10 18:00:00', NULL, 9, NULL),
(91, false, '2024-07-01 11:00:00', NULL, 10, NULL),
(92, false, '2024-07-02 12:00:00', NULL, 10, NULL),
(93, false, '2024-07-03 13:00:00', NULL, 10, NULL),
(94, false, '2024-07-04 14:00:00', NULL, 10, NULL),
(95, false, '2024-07-05 15:00:00', NULL, 10, NULL),
(96, false, '2024-07-06 16:00:00', NULL, 10, NULL),
(97, false, '2024-07-07 17:00:00', NULL, 10, NULL),
(98, false, '2024-07-08 18:00:00', NULL, 10, NULL),
(99, false, '2024-07-09 19:00:00', NULL, 10, NULL),
(100, false, '2024-07-10 20:00:00', NULL, 10, NULL);


-- Creando una receta medica para el primer paciente
INSERT INTO receta (fecha, id_medico, id_paciente, id_turno, clinica, diagnostico, tratamiento) VALUES
('2024-07-01', 1, 1, 1, 'Clinica Almedin', 'Diagnóstico ejemplo', 'Tratamiento ejemplo');

--Cambio el estado id_receta para que figure en el front: 
UPDATE turno
SET id_receta = 1
WHERE id_turno = 1;
