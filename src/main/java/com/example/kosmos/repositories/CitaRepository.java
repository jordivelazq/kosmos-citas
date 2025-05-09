package com.example.kosmos.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.kosmos.entities.Cita;
import com.example.kosmos.entities.Consultorio;
import com.example.kosmos.entities.Doctor;

public interface CitaRepository extends CrudRepository<Cita, Long> {
    boolean existsByDoctorAndFechaAndHora(Doctor d, LocalDate f, LocalTime h);
    boolean existsByConsultorioAndFechaAndHora(Consultorio c, LocalDate f, LocalTime h);
    List<Cita> findByPacienteAndFecha(String paciente, LocalDate fecha);
    long countByDoctorAndFecha(Doctor d, LocalDate f);
}
