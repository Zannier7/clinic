package com.zannier.exam.medico.infraestructure.controller;

import com.zannier.exam.especialidad.application.EspecialidadService;
import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.MedicoEspecialidadResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reporte-admin")
public class AdminReporteController {

    private static final String PATH_ADMIN = "src/main/resources/reportes/admin";

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @PostMapping("/medico-especialidad")
    public String medicoPorEspecialidad(@RequestParam("id")Integer idEspecialidad, HttpServletResponse response, ModelMap model) throws JRException, IOException {
        String pathDoc = "/MEDICO_ESPECIALIDAD.pdf";
        Collection<MedicoEspecialidadResponse> medicoEspecialidad = medicoService.obtenerMedicoPorEspecialidadReport(idEspecialidad);
        if (CollectionUtils.isEmpty(medicoEspecialidad)) {
            model.put("especialidades", especialidadService.obtenerEspecialidades());
            model.put("errorPDF", "No existen medicos con la especialidad " + especialidadService.obtenerEspecialidad(idEspecialidad).getName());
            return "/reportes/admin/medico-especialidad";
        }
        model.put("infoPDF", "Se descargo el PDF, revise su carpeta de descargas");
        byte[] data = createPDF(idEspecialidad, medicoEspecialidad);
        streamReport(response, data,"medicoespecialidad.pdf");
        return "/reportes/admin/medico-especialidad";
    }

    public byte[] createPDF(Integer idEspecialidad, Collection<MedicoEspecialidadResponse> medicosEspecialidad) throws JRException {
        JasperReport medicoEspecialidad = JasperCompileManager.compileReport(PATH_ADMIN + "/medicoEspecialidad.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(medicosEspecialidad);

        JasperPrint print = JasperFillManager.fillReport(medicoEspecialidad, parameters, source);

        return JasperExportManager.exportReportToPdf(print);
    }

    public void streamReport(HttpServletResponse response, byte[] data, String name) throws IOException {
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment; filename= " + name);
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
