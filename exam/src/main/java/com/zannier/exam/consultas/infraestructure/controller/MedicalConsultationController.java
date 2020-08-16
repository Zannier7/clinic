package com.zannier.exam.consultas.infraestructure.controller;

import com.zannier.exam.consultas.application.MedicalConsultationService;
import com.zannier.exam.consultas.domain.MedicalConsultation;
import com.zannier.exam.detailsconsultas.application.DetailsConsultasService;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.paciente.application.PacienteService;
import com.zannier.exam.paciente.domain.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/consulta")
public class MedicalConsultationController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicalConsultationService medicalConsultationService;

    @Autowired
    private DetailsConsultasService detailsConsultasService;

    @ModelAttribute("medicalconsultation")
    public String modulo(){
        return "medicalconsultation";
    }

    @GetMapping("/generar")
    public String medicalConsultation() {
        return "medicos/consultas/consultas";
    }

    @PostMapping("/registrar")
    public String registrarConsulta(@RequestParam("fecha")String fecha,
                                    @RequestParam("dni")String dniPaciente,
                                    @RequestParam("diagnostico")String diagnostico,
                                    @RequestParam("tratamiento")String tratamiento,
                                    HttpSession session, ModelMap model) {

        if (dniPaciente.length() == 8){
            Medico medico = (Medico) session.getAttribute("medicoLogin");

            LocalDate fechaFormat = LocalDate.parse(fecha);
            Paciente paciente = pacienteService.findPacienteByDocumento(dniPaciente);
            MedicalConsultation medicalConsultation = new MedicalConsultation();
            medicalConsultation.setCreateDate(fechaFormat);
            medicalConsultation.setMedicos(medico);
            medicalConsultation.setPacientes(paciente);

            MedicalConsultation medicalConsultation1 = medicalConsultationService.registrarConsultaMedica(medicalConsultation);
            DetailsConsultas detailsConsultas = new DetailsConsultas();
            detailsConsultas.setDiagnostic(diagnostico);
            detailsConsultas.setTreatment(tratamiento);
            detailsConsultas.setMedicalConsultation(medicalConsultation1);

            detailsConsultasService.registrarDetallesConsulta(detailsConsultas);
        }
        return "redirect:/consulta/generar";
    }
}
