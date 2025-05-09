package com.example.kosmos.services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kosmos.entities.Cita;
import com.example.kosmos.repositories.CitaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CitaService {
    @Autowired private CitaRepository citaRepo;

    private static final int MAX_CITAS_POR_DIA = 8;
    private static final Duration MIN_DIF_PACIENTE = Duration.ofHours(2);

    public Cita crearCita(Cita nueva) {
        // Regla 1: no más de 8 citas/día por doctor
        long cantidadCitas = citaRepo.countByDoctorAndFecha(nueva.getDoctor(), nueva.getFecha());
        if (cantidadCitas >= MAX_CITAS_POR_DIA) {
            throw new IllegalStateException("El doctor ya tiene 8 citas ese día.");
        }
        // Regla 2: mismo doctor misma hora
        if (citaRepo.existsByDoctorAndFechaAndHora(nueva.getDoctor(), nueva.getFecha(), nueva.getHora())) {
            throw new IllegalStateException("Doctor ya ocupado a esa hora.");
        }
        // Regla 3: mismo consultorio misma hora
        if (citaRepo.existsByConsultorioAndFechaAndHora(nueva.getConsultorio(), nueva.getFecha(), nueva.getHora())) {
            throw new IllegalStateException("Consultorio ocupado a esa hora.");
        }
        // Regla 4: paciente sin citas a la misma hora ni con <2h de diferencia
        List<Cita> citasPaciente = citaRepo.findByPacienteAndFecha(nueva.getPaciente(), nueva.getFecha());
        for (Cita c : citasPaciente) {
            long diff = Duration.between(c.getHora(), nueva.getHora()).abs().toHours();
            if (diff < MIN_DIF_PACIENTE.toHours() || c.getHora().equals(nueva.getHora())) {
                throw new IllegalStateException("Paciente debe esperar al menos 2 horas entre citas.");
            }
        }
        return citaRepo.save(nueva);
    }

    public List<Cita> listar(LocalDate fecha, Long doctorId, Long consultorioId) {
        List<Cita> all = new ArrayList<>();
        citaRepo.findAll().forEach(all::add);
        return all.stream()
            .filter(c -> (fecha == null || c.getFecha().equals(fecha)))
            .filter(c -> (doctorId == null || c.getDoctor().getId().equals(doctorId)))
            .filter(c -> (consultorioId == null || c.getConsultorio().getId().equals(consultorioId)))
            .toList();
    }

    public Cita obtenerPorId(Long id) {
        return citaRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
    }

    public Cita actualizarCita(Cita actualizada) {

        // Regla 1: no más de 8 citas/día por doctor
        long cantidadCitas = citaRepo.countByDoctorAndFecha(actualizada.getDoctor(), actualizada.getFecha());
        if (cantidadCitas >= MAX_CITAS_POR_DIA) {
            throw new IllegalStateException("El doctor ya tiene 8 citas ese día.");
        }
        // Regla 2: mismo doctor misma hora
        if (citaRepo.existsByDoctorAndFechaAndHora(actualizada.getDoctor(), actualizada.getFecha(), actualizada.getHora())) {
            throw new IllegalStateException("Doctor ya ocupado a esa hora.");
        }
        // Regla 3: mismo consultorio misma hora
        if (citaRepo.existsByConsultorioAndFechaAndHora(actualizada.getConsultorio(), actualizada.getFecha(), actualizada.getHora())) {
            throw new IllegalStateException("Consultorio ocupado a esa hora.");
        }
        // Regla 4: paciente sin citas a la misma hora ni con <2h de diferencia
        List<Cita> citasPaciente = citaRepo.findByPacienteAndFecha(actualizada.getPaciente(), actualizada.getFecha());
        for (Cita c : citasPaciente) {
            long diff = Duration.between(c.getHora(), actualizada.getHora()).abs().toHours();
            if (diff < MIN_DIF_PACIENTE.toHours() || c.getHora().equals(actualizada.getHora())) {
                throw new IllegalStateException("Paciente debe esperar al menos 2 horas entre citas.");
            }
        }

        return citaRepo.save(actualizada);
    }


    public void cancelar(Long id) {
        citaRepo.deleteById(id);
    }
}

