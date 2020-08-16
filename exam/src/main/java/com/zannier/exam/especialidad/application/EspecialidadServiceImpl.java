package com.zannier.exam.especialidad.application;

import com.zannier.exam.especialidad.domain.Especialidad;
import com.zannier.exam.especialidad.infraestructure.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EspecialidadServiceImpl implements EspecialidadService{

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public Collection<Especialidad> obtenerEspecialidades() {
        return (Collection<Especialidad>) especialidadRepository.findAll();
    }


    @Override
    public Especialidad registrarEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad obtenerEspecialidad(Integer idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad).orElse(null);
    }

    @Override
    public Integer actualizarEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad) != null ? 1 : 0;
    }

    @Override
    public Integer eliminarEspecialidad(Integer idEspecialidad) {
        especialidadRepository.deleteById(idEspecialidad);
        return 1;
    }
}
