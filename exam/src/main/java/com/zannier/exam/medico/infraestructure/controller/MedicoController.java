package com.zannier.exam.medico.infraestructure.controller;

import com.zannier.exam.especialidad.application.EspecialidadService;
import com.zannier.exam.especialidad.domain.Especialidad;
import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @ModelAttribute("medico")
    public String modulo() {
        return "medico";
    }


    @GetMapping("/medicos")
    public String listarMedicos(ModelMap model) {
        Collection<Medico> medicos = medicoService.obtenerMedicos();
        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        model.put("medicos", medicos);
        model.put("especialidades", especialidades);

        return "medicos/medicos";
    }

    @GetMapping("/reporte/especialidad")
    public String reporteMedicosPorEspecialidad(ModelMap model) {
        model.put("especialidad", new Especialidad());
        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        model.put("especialidades", especialidades);
        return "/reportes/admin/medico-especialidad";
    }

    @PostMapping("/buscar")
    public String  buscarMedicoPorEspecialidad(@RequestParam("id")Integer idEspecialidad, ModelMap model) {
        Collection<Medico> medicos = medicoService.obtenerMedicoPorEspecialidad(idEspecialidad);
        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        if (CollectionUtils.isEmpty(medicos)){
            model.put("especialidades", especialidades);
            model.put("errorOnline", "No existen Medicos con la Especialidad de " + especialidadService.obtenerEspecialidad(idEspecialidad).getName());
            return "/reportes/admin/medico-especialidad";
        }
        model.put("especialidades", especialidades);
        model.put("medicos", medicos);
        return "/reportes/admin/medico-especialidad";
    }

    @GetMapping("/nuevo")
    public String nuevoMedico(ModelMap model){
        model.put("medicoNuevo", new Medico());
        model.put("especialidadMedico", new Especialidad());
        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        model.put("especialidades", especialidades);

        return "medicos/medico-nuevo";
    }

    @PostMapping("/registrar")
    public String registrarMedico(@Valid @ModelAttribute(value = "medicoNuevo")Medico medico,@RequestParam("idEspecialidad")Integer especialidadMedico, BindingResult bindingResult, ModelMap model){

        String resultPage = "";

        if (especialidadMedico < 1) {
            model.put("err", "Hubo un error en la especialidad, vuelva a realizar el registro");
            return "/medicos/medico-nuevo";
        }

        Especialidad especialidad = especialidadService.obtenerEspecialidad(especialidadMedico);
        medico.setEspecialidad(especialidad);

        String passwordEncrypt = bCryptPasswordEncoder.encode(medico.getClave());
        medico.setClave(passwordEncrypt);

        if (bindingResult.hasErrors()){
            Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
            model.put("especialidades", especialidades);
            return "medicos/medico-nuevo";
        } else {

            Medico nuevoMedico = medicoService.registrarMedico(medico);

            if (nuevoMedico != null) {
                resultPage = "redirect:/medico/medicos";
            } else {
                model.put("error", "No se registro el paciente");
                resultPage = "/medicos/medico-nuevo";
            }
        }
        return resultPage;
    }

    @GetMapping("/especialidad/{id}")
    public String setIdMedicoChangeEspecialidad(@PathVariable(value = "id")Integer idMedico, ModelMap model){
        Medico medico = medicoService.obtenerMedico(idMedico);
        Integer idMedicoEspecialidad = medico.getId();
        model.put("idMedico", idMedicoEspecialidad);

        Collection<Especialidad> especialidades = especialidadService.obtenerEspecialidades();
        model.put("especialidades", especialidades);

        return "medicos/medico-change-especialidad";
    }

    @PostMapping("/actualizar/especialidad")
    public String actualizarEspecialidadMedico(
            @RequestParam("idMedico")Integer idMedico,
            @RequestParam("especialidad-e")Integer idEspecialidad,
            ModelMap model){
        String returnPath = null;
        if (idEspecialidad < 1) {
            model.put("error", "Seleccione una especialidad");
            model.put("idMedico", idMedico);
            return "medicos/medico-change-especialidad";
        }
        Integer update = medicoService.updateEspecialidadMedico(idMedico, idEspecialidad);
        if (update == 1) {
            returnPath = "redirect:/medico/medicos";
        } else {
            model.put("error", "No se pudo actualizar la especialidad. Intentelo mas tarde");
            model.put("idMedico", idMedico);
            returnPath =  "medicos/medico-change-especialidad";
        }
        return returnPath;
    }

    @GetMapping("/password/{id}")
    public String setIdMedicoChangePassword(@PathVariable(value = "id")Integer idMedico, ModelMap model){
        Medico medico = medicoService.obtenerMedico(idMedico);
        Integer idMedicoEditar = medico.getId();
        model.put("medico", idMedicoEditar);
        return "medicos/medico-change-password";

    }

    @PostMapping("/actualizar/password")
    public String cambiarPassword(@RequestParam("idMedico")Integer idMedico,
                                  @RequestParam("medicoPassword")String medicoPassword,
                                  ModelMap model){
        String returnPath = null;
        if (medicoPassword.isEmpty()) {
            model.put("error", "Escriba una nueva contraseña");
            model.put("medico", idMedico);
            return "medicos/medico-change-password";
        }
        String passwordEncrypt = bCryptPasswordEncoder.encode(medicoPassword);
        Integer medico = medicoService.updatePasswordMedico(idMedico, passwordEncrypt);

        if (medico == 1) {
            returnPath = "redirect:/medico/medicos";
        } else {
            model.put("error", "No se pudo actualizar la contraseña. Intentelo mas tarde");
            model.put("medico", idMedico);
            returnPath =  "medicos/medico-change-password";
        }
        return returnPath;
    }

    @GetMapping("/editar/{id}")
    public String editarMedico(@PathVariable(value = "id")Integer idMedico, ModelMap model){
        Medico medico = medicoService.obtenerMedico(idMedico);
        model.put("especialidadMedico", new Especialidad());
        Collection<Especialidad> esp = especialidadService.obtenerEspecialidades();
        model.put("medicoEditar", medico);
        model.put("especialidades", esp);
        return "medicos/medico-editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMedico(
            @Valid
            @ModelAttribute("medicoEditar")Medico medicoEditar,
            BindingResult bindingResult, ModelMap model){

        String resultPage = null;
        if (bindingResult.hasErrors()){
            Collection<Especialidad> esp = especialidadService.obtenerEspecialidades();
            model.put("error", "Error en los campos verifique el formulario");
            model.put("especialidades", esp);
            resultPage = "/medicos/medico-editar";
        } else {
            int resultado = medicoService.actualizarMedico(medicoEditar);

            if (resultado > 0) {
                resultPage = "redirect:/medico/medicos";
            } else {
                model.put("error", "No se actualizo los datos del medico");
                resultPage = "redirect:/medico/medicos";
            }
        }
        return resultPage;
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<MedicoController.EliminarResponse> eliminarMedico(@PathVariable(value = "id") Integer idMedico) {
        Integer row = medicoService.eliminarMedico(idMedico);
        ResponseEntity<MedicoController.EliminarResponse> response = null;

        if (row > 0) {
            response = new ResponseEntity<MedicoController.EliminarResponse>(new MedicoController.EliminarResponse("Se eliminó correctamente"),
                    HttpStatus.OK);
        } else {
            response = new ResponseEntity<MedicoController.EliminarResponse>(new MedicoController.EliminarResponse("No se eliminó"),
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
