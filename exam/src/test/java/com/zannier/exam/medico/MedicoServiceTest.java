package com.zannier.exam.medico;

import com.zannier.exam.especialidad.application.EspecialidadService;
import com.zannier.exam.especialidad.domain.Especialidad;
import com.zannier.exam.medico.application.MedicoService;
import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.paciente.domain.Paciente;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Documented;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
public class MedicoServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(MedicoServiceTest.class);
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("Registrar Medico")
    @Commit
    public void registrarMedico() {
        Especialidad especialidad = especialidadService.obtenerEspecialidad(1);
        LOG.info(especialidad.toString());
        Medico medico = new Medico();
        medico.setFirstName("ARMANDO JUAN");
        medico.setLastName("POMA ROCA");
        medico.setDni("77777777");
        medico.setCmp("100");
        medico.setRol("ADMIN");
        medico.setClave(bCryptPasswordEncoder.encode("1234567890"));
        medico.setEspecialidad(especialidad);

        LOG.info(medico.toString());
        Medico newMedico = medicoService.registrarMedico(medico);

        assertNotNull(newMedico, "Medico Registrado");
    }

    @Test
    @DisplayName("Actualizando Datos Medico")
    @Commit
    @Disabled
    public void updateDatosMedico() {
        Especialidad especialidad = especialidadService.obtenerEspecialidad(31);
        LOG.info(especialidad.toString());
        Medico medico = medicoService.obtenerMedico(4);
        medico.setFirstName("LEOPOLDO LUCAS");
        medico.setLastName("TORRES ALVA");
        medico.setDni("16157732");
        medico.setCmp("1098");
        medico.setEspecialidad(especialidad);
        LOG.info(medico.toString());

        int updated = medicoService.actualizarMedico(medico);

        assertTrue(updated>0, "Datos actualizados correctamente");
    }

    @Test
    @DisplayName("Eliminando Datos Cliente")
    @Commit
    @Disabled
    public void eliminarDatosMedico(){
        int delete = medicoService.eliminarMedico(4);
        assertTrue(delete > 0, "Medico eliminado correctamente");
    }

    @Test
    @DisplayName("Obtener Datos Medico Por ID")
    public void obtenerDatosMedico() {
        Medico medico = medicoService.obtenerMedico(2);
        LOG.info(medico.toString());
        assertNotNull(medico, "Datos obtenidos correctamente");
    }

    @Test
    @DisplayName("Obteniendo Lista de Medicos")
    public void obtenerPacientes(){
        Optional<Collection<Medico>> medicos = Optional.of(medicoService.obtenerMedicos());
        medicos.ifPresent(medico -> LOG.info(medico.toString()));

        assertTrue(medicos.isPresent(), "Lista de medicos encontrados");
    }

    @Test
    @DisplayName("Lista Medicos por Especialidad")
    public void obtenerMedicosPorEspecialidad(){
        Optional<Collection<Medico>> medicosPorEspecialidad = Optional.of(medicoService.obtenerMedicoPorEspecialidad(25));
        medicosPorEspecialidad.ifPresent(medico -> LOG.info(medico.toString()));

        assertTrue(medicosPorEspecialidad.isPresent(), "Lista de Medicos Por Especialidad");
    }

    @Test
    @DisplayName("Obtener Datos Medico Por Documento")
    public void obtenerMedicoPorDocumento(){
        Medico medico = medicoService.obtenerPorDocumento("1234567");
        LOG.info(medico.toString());
        assertNotNull(medico, "Medico encontrado");

    }
}
