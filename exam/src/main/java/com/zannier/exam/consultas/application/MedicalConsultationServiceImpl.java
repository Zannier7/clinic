package com.zannier.exam.consultas.application;

import com.zannier.exam.consultas.domain.MedicalConsultation;
import com.zannier.exam.consultas.infraestructure.repository.MedicalConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicalConsultationServiceImpl implements MedicalConsultationService{

    @Autowired
    private MedicalConsultationRepository medicalConsultationRepository;

    @Override
    public Collection<MedicalConsultation> obtenerConsultasMedicas() {
        return (Collection<MedicalConsultation>) medicalConsultationRepository.findAll();
    }

    @Override
    public MedicalConsultation registrarConsultaMedica(MedicalConsultation medicalConsultation) {
        return medicalConsultationRepository.save(medicalConsultation);
    }

    @Override
    public MedicalConsultation obtenerConsultaMedica(Integer idConsultaMedica) {
        return medicalConsultationRepository.findById(idConsultaMedica).orElse(null);
    }

    @Override
    public Integer actualizarConsultaMedica(MedicalConsultation medicalConsultation) {
        return medicalConsultationRepository.save(medicalConsultation) != null ? 1:0;
    }

    @Override
    public Integer eliminarConsultaMedica(Integer idConsultaMedica) {
        medicalConsultationRepository.deleteById(idConsultaMedica);
        return 1;
    }
}
