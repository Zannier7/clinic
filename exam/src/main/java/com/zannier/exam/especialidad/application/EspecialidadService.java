package com.zannier.exam.especialidad.application;

import com.zannier.exam.especialidad.domain.Especialidad;

import java.util.Collection;

public interface EspecialidadService {

    public Collection<Especialidad> obtenerEspecialidades();
    public Especialidad registrarEspecialidad(Especialidad especialidad);
    public Especialidad obtenerEspecialidad(Integer idEspecialidad);
    public Integer actualizarEspecialidad(Especialidad especialidad);
    public Integer eliminarEspecialidad(Integer idEspecialidad);
}
