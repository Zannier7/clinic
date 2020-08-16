package com.zannier.exam.medico.infraestructure.controller;

import com.zannier.exam.detailsconsultas.application.DetailsConsultasService;
import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
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
@RequestMapping("/reporte-medico")
public class MedicoReporteController {

    private static final String PATH_MEDICO = "src/main/resources/reportes/medico";

    @Autowired
    private DetailsConsultasService detailsConsultasService;

    @PostMapping("/paciente-historial")
    public String pacienteHistorialClinico(@RequestParam("dni")String dniPaciente, HttpServletResponse response, ModelMap model) throws JRException, IOException {
        String pathDoc = "/paciente_historial.pdf";
        Collection<ConsultasPacienteResponse> consultasPaciente = detailsConsultasService.consultasMedicasPorPaciente(dniPaciente);
        if (CollectionUtils.isEmpty(consultasPaciente)){
            model.put("errorPDF", "No existe historial clinico del paciente o no ingresaste un DNI valido");
            return "/reportes/medico/historia-clinica-paciente";
        }
        byte[] data = createPDF(dniPaciente, consultasPaciente);
        streamReport(response, data, "pacientehistorial.pdf");
        return "/reportes/medico/historia-clinica-paciente";
    }

    public byte[] createPDF(String dniPaciente, Collection<ConsultasPacienteResponse> consultasPaciente) throws JRException {
        JasperReport historialPaciente = JasperCompileManager.compileReport(PATH_MEDICO + "/historialPaciente.jrxml");
        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(consultasPaciente);

        JasperPrint print = JasperFillManager.fillReport(historialPaciente, parameters, source);

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
