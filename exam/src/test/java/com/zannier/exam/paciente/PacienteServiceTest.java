package com.zannier.exam.paciente;

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

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
public class PacienteServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Autowired
    private PacienteService pacienteService;

    @Test
    @DisplayName("Registrar Paciente")
    @Commit
    @Disabled
    public void registrarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setFirstName("DEYVIS");
        paciente.setLastName("GARCIA CERCADO");
        paciente.setDni("76587932");
        paciente.setNumeroHistoriaClinica("122");

        Paciente newPaciente = pacienteService.registrarPaciente(paciente);

        assertNotNull(newPaciente, "Pacinete registrado correctamente");
    }

    @Test
    @DisplayName("Actualizar Datos Paciente")
    @Commit
    @Disabled
    public void updateDatosPaciente() {
        Paciente paciente = pacienteService.obtenerPaciente(2);
        LOG.info(paciente.toString());
        paciente.setFirstName("DEYVIS RONALD");
        paciente.setNumeroHistoriaClinica("120");
        LOG.info(paciente.toString());
        int updated = pacienteService.actualizarPaciente(paciente);

        assertTrue(updated>0, "Datos actualizado correctamente");
    }

    @Test
    @DisplayName("Paciente Eliminado")
    @Commit
    @Disabled
    public void eliminarDatosPaciente(){
        int delete = pacienteService.eliminarPaciente(2);
        assertTrue(delete>0, "Paciente eliminado correctamente");
    }

    @Test
    @DisplayName("Obtener Datos Paciente Por ID")
    public void obtenerDatosPaciente(){
        Paciente paciente = pacienteService.obtenerPaciente(1);
        LOG.info(paciente.toString());
        assertNotNull(paciente,"Datos obtenidos correctamente");
    }

    @Test
    @DisplayName("Obteniendo Lista de Pacientes")
    public void obtenerPacientes(){
        Optional<Collection<Paciente>> pacientes = Optional.of(pacienteService.obtenerPacientes());
        pacientes.ifPresent(paciente -> LOG.info(pacientes.toString()));

        assertTrue(pacientes.isPresent(), "Lista de pacientes encontrados");
    }
}
