package com.example.kosmos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.kosmos.entities.Consultorio;
import com.example.kosmos.entities.Doctor;
import com.example.kosmos.repositories.ConsultorioRepository;
import com.example.kosmos.repositories.DoctorRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired private DoctorRepository dr;
    @Autowired private ConsultorioRepository cr;

    @Override
    public void run(String... args) throws Exception {
        dr.saveAll(List.of(
          new Doctor(null,"Ana","Pérez","Gómez","Cardiología"),
          new Doctor(null,"Luis","Martínez","Díaz","Endocrinología"),
          new Doctor(null,"María","López","Fernández","Neurología")
        ));
        cr.saveAll(List.of(
          new Consultorio(null,101,1),
          new Consultorio(null,201,2),
          new Consultorio(null,301,3)
        ));
    }
}

