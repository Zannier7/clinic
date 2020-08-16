package com.zannier.exam.detailsconsultas.infraestructure.controller;

import com.zannier.exam.detailsconsultas.application.DetailsConsultasService;
import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import com.zannier.exam.paciente.application.PacienteService;
import com.zannier.exam.paciente.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/detallesConsulta")
public class DetailsConsultasController {

    @Autowired
    private DetailsConsultasService detailsConsultasService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/consultasPaciente")
    public String principalConsulta(){
        return "medicos/consultas/consulta-buscar";
    }

    @PostMapping("/buscar")
    public String buscarDetalleConsulta(@RequestParam("dni")String dniPaciente, ModelMap model){
        if (dniPaciente.isEmpty()) {
            model.put("error", "Ingrese el dni del paciente");
            return "/medicos/consultas/consulta-buscar";
        }

        Paciente paciente = pacienteService.findPacienteByDocumento(dniPaciente);
        model.put("paciente", paciente);

        if (paciente == null) {
            model.put("error", "No se encontraron consultas medicas con este DNI " + dniPaciente);
            return "/medicos/consultas/consulta-buscar";
        }

        List<DetailsConsultas> detailsConsultas = detailsConsultasService.findDetallesConsultas(dniPaciente);

        model.put("detailsConsulta", detailsConsultas);

        return "/medicos/consultas/consulta-buscar";
    }

    @GetMapping("/historia")
    public String historiaClinicaPaciente(){
        return "/reportes/medico/historia-clinica-paciente";
    }

    @PostMapping("/buscar-historial")
    public String buscarHistorialClinicoPaciente(@RequestParam("dni")String dniPaciente, ModelMap model) {
        if (dniPaciente.isEmpty()) {
            model.put("errorOnline", "Ingrese el DNI del paciente");
            return "/reportes/medico/historia-clinica-paciente";
        }
        Paciente paciente = pacienteService.findPacienteByDocumento(dniPaciente);
        if (paciente == null){
            model.put("errorOnline", "No existen datos de asociados a este DNI " + dniPaciente);
            return "/reportes/medico/historia-clinica-paciente";
        }
        List<ConsultasPacienteResponse> historialPaciente = detailsConsultasService.consultasMedicasPorPaciente(dniPaciente);

        model.put("paciente", paciente);

        model.put("historialPaciente", historialPaciente);

        return "/reportes/medico/historia-clinica-paciente";
    }
}
