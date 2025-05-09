package com.example.kosmos.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.kosmos.entities.Cita;
import com.example.kosmos.repositories.ConsultorioRepository;
import com.example.kosmos.repositories.DoctorRepository;
import com.example.kosmos.services.CitaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/citas")
public class CitaController {
    @Autowired private CitaService citaService;
    @Autowired private DoctorRepository doctorRepo;
    @Autowired private ConsultorioRepository consultorioRepo;

    @GetMapping
    public String viewCitas(
        @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE) LocalDate fecha,
        @RequestParam(required=false) Long doctor,
        @RequestParam(required=false) Long consultorio,
        Model model)
    {
        model.addAttribute("lista", citaService.listar(fecha, doctor, consultorio));
        model.addAttribute("doctores", doctorRepo.findAll());
        model.addAttribute("consultorios", consultorioRepo.findAll());
        return "citas";  // src/main/resources/templates/citas.html
    }

    @GetMapping("/nuevo")
    public String formNuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("doctores", doctorRepo.findAll());
        model.addAttribute("consultorios", consultorioRepo.findAll());
        model.addAttribute("minFecha", LocalDate.now());
        return "cita_form";  // templates/cita_form.html
    }

    @PostMapping("/nuevo")
    public String guardar(
        @Valid @ModelAttribute("cita") Cita cita,
        BindingResult binding,
        Model model,
        RedirectAttributes flash
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("doctores", doctorRepo.findAll());
            model.addAttribute("consultorios", consultorioRepo.findAll());
            return "cita_form";
        }

        try {
            citaService.crearCita(cita);
            flash.addFlashAttribute("ok","Cita agendada.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/citas/nuevo";
        }
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cita cita = citaService.obtenerPorId(id);
        model.addAttribute("cita", cita);
        model.addAttribute("doctores", doctorRepo.findAll());
        model.addAttribute("consultorios", consultorioRepo.findAll());
        model.addAttribute("minFecha", LocalDate.now());
        return "cita_edit";
    }

    @PostMapping("/editar")
    public String actualizarCita(
        @Valid @ModelAttribute("cita") Cita cita,
        BindingResult binding,
        Model model,
        RedirectAttributes flash) {

        if (binding.hasErrors()) {
            model.addAttribute("doctores", doctorRepo.findAll());
            model.addAttribute("consultorios", consultorioRepo.findAll());
            return "cita_edit";
        }

        try {
            citaService.actualizarCita(cita);
            flash.addFlashAttribute("ok", "Cita actualizada.");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/citas/editar/" + cita.getId();
        }

        return "redirect:/citas";
    }


    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return "redirect:/citas";
    }
}

