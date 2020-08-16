package com.zannier.exam.especialidad.infraestructure.controller;

import com.zannier.exam.especialidad.application.EspecialidadService;
import com.zannier.exam.especialidad.domain.Especialidad;
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
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @ModelAttribute("especialidad")
    public String modulo() {
        return "especialidades";
    }

    @GetMapping("/especialidades")
    public String listadoEspecialidades(ModelMap model){
        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        model.put("especialidades", especialidades);
        return "/especialidades/especialidades";
    }

    @GetMapping("/nueva")
    public String especialidadNueva(ModelMap model){
        model.put("especialidadNuevo", new Especialidad());
        return "especialidades/especialidad-nueva";
    }

    @PostMapping("/registrar")
    public String registrarEspecialidad(@Valid @ModelAttribute("especialidadNuevo")Especialidad especialidad, BindingResult bindingResult, ModelMap model){
        String response = "redirect:/especialidad/especialidades";
        if (bindingResult.hasErrors()){
            model.put("error", "Complete todos los campos");
            return "especialidades/especialidad-nueva";
        } else {
            Especialidad nuevaEspecialidad = especialidadService.registrarEspecialidad(especialidad);
            if (nuevaEspecialidad != null) {
                return response;
            } else {
                model.put("error", "No se registro la especialidad");
                return response;
            }
        }
    }

    @GetMapping("/editar/{id}")
    public String editarEspecialidad(@PathVariable(value = "id")Integer idEspecialidad, ModelMap model){
        Especialidad especialidad = especialidadService.obtenerEspecialidad(idEspecialidad);
        model.put("especialidadEditar", especialidad);
        return "especialidades/especialidad-editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarEspecialidad(@Valid @ModelAttribute("especialidadEditar")Especialidad especialidad, BindingResult bindingResult, ModelMap model){
        String responsePage = "redirect:/especialidad/especialidades";

        if (bindingResult.hasErrors()){
            return "especialidades/especialidad-editar";
        } else {
            int resultado = especialidadService.actualizarEspecialidad(especialidad);

            if (resultado > 0) {
                return responsePage;
            } else {
                model.put("error", "No se registro la especialidad");
                return responsePage;
            }
        }
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<EspecialidadController.EliminarResponse> eliminarEspecialidad(@PathVariable(value = "id") Integer idEspecialidad) {
        Integer row = especialidadService.eliminarEspecialidad(idEspecialidad);
        ResponseEntity<EspecialidadController.EliminarResponse> response = null;

        if (row > 0) {
            response = new ResponseEntity<EspecialidadController.EliminarResponse>(new EspecialidadController.EliminarResponse("Se eliminó correctamente"),
                    HttpStatus.OK);
        } else {
            response = new ResponseEntity<EspecialidadController.EliminarResponse>(new EspecialidadController.EliminarResponse("No se eliminó"),
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
