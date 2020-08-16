package com.zannier.exam.paciente.infraestructure.controller;

import com.zannier.exam.paciente.application.PacienteService;
import com.zannier.exam.paciente.domain.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @ModelAttribute("paciente")
    public String modulo(){
        return "pacientes";
    }

    @GetMapping("/pacientes")
    public String listadoPacientes(ModelMap model){
        Collection<Paciente> pacientes = pacienteService.obtenerPacientes();
        model.put("pacientes", pacientes);

        return "pacientes/pacientes";
    }

    @GetMapping("/nuevo")
    public String pacienteNuevo(ModelMap model){
        model.put("pacienteNuevo", new Paciente());
        return "pacientes/paciente-nuevo";
    }

    @PostMapping("/registrar")
    public String registrarPaciente(@Valid @ModelAttribute("pacienteNuevo") Paciente paciente, BindingResult bindingResult, ModelMap model){

        String resultPage = "";

        if (bindingResult.hasErrors()){
            return "pacientes/paciente-nuevo";
        } else {
            Paciente nuevoPaciente = pacienteService.registrarPaciente(paciente);
            if (nuevoPaciente != null) {
                resultPage = "redirect:/paciente/pacientes";
            } else {
                model.put("error", "No se registro el paciente");
                resultPage = "redirect:/paciente/pacientes";
            }
        }
        return resultPage;
    }

    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable(value = "id")Integer idPaciente, ModelMap model) {
        Paciente paciente = pacienteService.obtenerPaciente(idPaciente);
        model.put("pacienteEditar", paciente);

        return "pacientes/paciente-editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarPaciente(
            @Valid
            @ModelAttribute("pacienteEditar")
                    Paciente paciente,
            BindingResult bindingResult, ModelMap model) {
        String resultPage = "";

        if (bindingResult.hasErrors()) {
            resultPage = "pacientes/paciente-editar";
        } else {
            int resultado = pacienteService.actualizarPaciente(paciente);

            if (resultado > 0) {
                resultPage = "redirect:/paciente/pacientes";
            } else {
                model.put("error", "No se actualizo los datos del paciente");
                resultPage = "redirect:/paciente/pacientes";
            }
        }

        return resultPage;
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<EliminarResponse> eliminarPaciente(@PathVariable(value = "id") Integer idPaciente) {
        Integer row = pacienteService.eliminarPaciente(idPaciente);
        ResponseEntity<EliminarResponse> response = null;

        if (row > 0) {
            response = new ResponseEntity<EliminarResponse>(new EliminarResponse("Se eliminó correctamente"),
                    HttpStatus.OK);
        } else {
            response = new ResponseEntity<EliminarResponse>(new EliminarResponse("No se eliminó"),
                    HttpStatus.EXPECTATION_FAILED);
        }

        return response;
    }

    @Data
    @AllArgsConstructor
    class EliminarResponse {
        private String message;
    }
}

