package com.example.kosmos.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="doctor_id")
    @NotNull(message="El doctor es obligatorio")
    private Doctor doctor;

    @ManyToOne @JoinColumn(name="consultorio_id")
    @NotNull(message="El consultorio es obligatorio")
    private Consultorio consultorio;

    @NotNull(message="La fecha es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    @NotNull(message="La hora es obligatoria")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora;
    @NotBlank(message="El nombre del paciente es obligatorio")
    private String paciente;
}