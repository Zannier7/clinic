package com.zannier.exam.medico.application;

import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.medico.domain.MedicoEspecialidadResponse;
import com.zannier.exam.medico.infraestructure.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicoServiceImpl implements MedicoService{

    @Autowired
    private MedicoRepository medicoRepository;


    @Override
    public Collection<Medico> obtenerMedicos() {
        return (Collection<Medico>) medicoRepository.findAll();
    }

    @Override
    public Medico registrarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public Medico obtenerMedico(Integer idMedico) {
        return medicoRepository.findById(idMedico).orElse(null);
    }

    @Override
    public Integer actualizarMedico(Medico medico) {
        return medicoRepository.save(medico) != null ? 1:0;
    }

    @Override
    public Integer eliminarMedico(Integer idMedico) {
        medicoRepository.deleteById(idMedico);
        return 1;
    }

    @Override
    public Collection<Medico> obtenerMedicoPorEspecialidad(Integer idEspecialidad) {
        return medicoRepository.findMedicoByEspecialidad(idEspecialidad);
    }

    @Override
    public Collection<MedicoEspecialidadResponse> obtenerMedicoPorEspecialidadReport(Integer idEspecialidad) {
        return medicoRepository.findMedicoByEspecialidadReport(idEspecialidad);
    }

    @Override
    public Medico obtenerPorDocumento(String documento) {
        return medicoRepository.findByDocumento(documento);
    }

    @Override
    public Integer updatePasswordMedico(Integer idMedico, String nuevaClave) {
        return medicoRepository.updatePasswordMedico(idMedico, nuevaClave);
    }

    @Override
    public Integer updateEspecialidadMedico(Integer idMedico, Integer nuevaEspecialidad) {
        return medicoRepository.updateEspecialidadMedico(idMedico, nuevaEspecialidad);
    }
}
