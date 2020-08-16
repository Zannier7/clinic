package com.zannier.exam.detalleconsulta;

import com.zannier.exam.consultas.application.MedicalConsultationService;
import com.zannier.exam.consultas.domain.MedicalConsultation;
import com.zannier.exam.detailsconsultas.application.DetailsConsultasService;
import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
public class DetalleConsultasServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(DetalleConsultasServiceTest.class);

    @Autowired
    private DetailsConsultasService detailsConsultasService;

    @Autowired
    private MedicalConsultationService medicalConsultationService;

    @Test
    @DisplayName("Registrando Detalles Consulta")
    @Commit
    @Disabled
    public void registrarDetallesConsulta() {
        MedicalConsultation medicalConsultation = medicalConsultationService.obtenerConsultaMedica(3);
        LOG.info(medicalConsultation.toString());

        DetailsConsultas detailsConsultas = new DetailsConsultas();
        detailsConsultas.setDiagnostic("TIENES CANCER PRONTO MORIRAS");
        detailsConsultas.setTreatment("REVIVE TUS DIAS CON TU BABY");
        detailsConsultas.setMedicalConsultation(medicalConsultation);

        DetailsConsultas newDetailsConsulta = detailsConsultasService.registrarDetallesConsulta(detailsConsultas);
        LOG.info(newDetailsConsulta.toString());

        assertNotNull(newDetailsConsulta, "Detalles Consulta Registrado");
    }

    @Test
    @DisplayName("Obtener consultas del paciente")
    public void obtenerConsultasPaciente() {
        List<ConsultasPacienteResponse> consultas = detailsConsultasService.consultasMedicasPorPaciente("1444");

        consultas.stream().forEach((p) -> LOG.info(p.toString()));


        assertNotNull(consultas, "LISTA DE CONSULTAS");
    }

    @Test
    @DisplayName("Detalles consulta por pacientes")
    public void obtenerDetallesConsultas() {
        List<DetailsConsultas> detailsConsultasLis = detailsConsultasService.findDetallesConsultas("1444");

        detailsConsultasLis.stream().forEach((detalleConsulta) -> LOG.info(detalleConsulta.toString()));

        assertNotNull(detailsConsultasLis, "Detalles consultas medicas");

    }
}
