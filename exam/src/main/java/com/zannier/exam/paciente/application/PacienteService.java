package com.zannier.exam.paciente.application;

import com.zannier.exam.paciente.domain.Paciente;

import java.util.Collection;

public interface PacienteService {

    public Collection<Paciente> obtenerPacientes();
    public Paciente registrarPaciente(Paciente paciente);
    public Paciente obtenerPaciente(Integer idPaciente);
    public Paciente findPacienteByDocumento(String dniPaciente);
    public Integer actualizarPaciente(Paciente paciente);
    public Integer eliminarPaciente(Integer idPaciente);
}
