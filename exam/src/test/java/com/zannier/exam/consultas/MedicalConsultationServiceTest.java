package com.zannier.exam.consultas;


import com.zannier.exam.consultas.application.MedicalConsultationService;
import com.zannier.exam.consultas.domain.MedicalConsultation;
import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.paciente.application.PacienteService;

import com.zannier.exam.paciente.domain.Paciente;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration
public class MedicalConsultationServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(MedicalConsultationServiceTest.class);

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicalConsultationService medicalConsultationService;

    @Test
    @DisplayName("Registrar Consulta Medica")
    @Commit
    @Disabled
    public void registrarConsultaMedica() {
        Paciente paciente = pacienteService.obtenerPaciente(3);
        LOG.info(paciente.toString());
        Medico medico = medicoService.obtenerMedico(3);
        LOG.info(medico.toString());

        MedicalConsultation medicalConsultation = new MedicalConsultation();
        medicalConsultation.setCreateDate(LocalDate.now());
        medicalConsultation.setMedicos(medico);
        medicalConsultation.setPacientes(paciente);
        LOG.info(medicalConsultation.toString());

        MedicalConsultation newMedicalConsultation = medicalConsultationService.registrarConsultaMedica(medicalConsultation);

        assertNotNull(newMedicalConsultation, "Consulta Registrada");
    }
}
