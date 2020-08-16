package com.zannier.exam.paciente.application;

import com.zannier.exam.paciente.domain.Paciente;
import com.zannier.exam.paciente.infraestructure.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Collection<Paciente> obtenerPacientes() {
        return (Collection<Paciente>) pacienteRepository.findAll();
    }

    @Override
    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente obtenerPaciente(Integer idPaciente) {
        return pacienteRepository.findById(idPaciente).orElse(null);
    }

    @Override
    public Paciente findPacienteByDocumento(String dniPaciente) {
        return pacienteRepository.findByDocumento(dniPaciente);
    }

    @Override
    public Integer actualizarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente) != null ? 1:0;
    }

    @Override
    public Integer eliminarPaciente(Integer idPaciente) {
        pacienteRepository.deleteById(idPaciente);
        return 1;
    }
}
