package com.zannier.exam.especialidad;

import com.zannier.exam.especialidad.application.EspecialidadService;
import com.zannier.exam.especialidad.domain.Especialidad;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class EspecialidadServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(EspecialidadServiceTest.class);

    @Autowired
    private EspecialidadService especialidadService;

    @Test
    @DisplayName("Registrar Especialidad")
    @Commit
    public void registrarEspecialidad()
    {
        Especialidad especialidad = new Especialidad();
        especialidad.setName("NEUROLOGIA");

        Especialidad esp = especialidadService.registrarEspecialidad(especialidad);
        LOG.info("Especialidad: " + esp.toString());

        assertTrue(esp.getName() == "NEUROLOGIA","Registro Exitoso " + especialidad.toString());
    }

    @Test
    @Disabled
    @DisplayName("Obtener Especialidad Por ID")
    public void obtenerEspecialidad()
    {
        Especialidad especialidad = especialidadService.obtenerEspecialidad(3);
        LOG.info("Especialidad: " + especialidad.toString());

        assertNotNull(especialidad,"No se pudo obtener la especialidad especificada");
    }

    @Test
    @DisplayName("Eliminar Especialidad")
    @Rollback(false)
    @Disabled
    public void eliminarEspecialidad()
    {
        Integer filas = especialidadService.eliminarEspecialidad(3);

        assertTrue(filas>0, "Algo pasara");
    }

    @Test
    @DisplayName("Actualizar Especialidad")
    @Commit
    public void actualizarEspecialidad() {
        Especialidad esp = especialidadService.obtenerEspecialidad(8);
        LOG.info(esp.toString());
        esp.setName("VIROLOGIA");
        int a = especialidadService.actualizarEspecialidad(esp);
        LOG.info(esp.toString() + a);

        assertTrue(a>0,"Paso la prueba");
    }

    @Test
    @DisplayName("Listando Especialidades")
    public void listarEspecialidades()
    {
        Optional<Collection<Especialidad>> especialidads = Optional.of(especialidadService.obtenerEspecialidades());
        especialidads.ifPresent(especialidad -> LOG.info(especialidad.toString()));

        assertTrue(especialidads.isPresent(), "Especialidades encontradas");
    }
}
