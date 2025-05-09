package com.example.kosmos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.kosmos.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
