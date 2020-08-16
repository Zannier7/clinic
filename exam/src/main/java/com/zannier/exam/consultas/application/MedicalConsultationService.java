package com.zannier.exam.consultas.application;

import com.zannier.exam.consultas.domain.MedicalConsultation;

import java.util.Collection;

public interface MedicalConsultationService {

    public Collection<MedicalConsultation> obtenerConsultasMedicas();
    public MedicalConsultation registrarConsultaMedica(MedicalConsultation medicalConsultation);
    public MedicalConsultation obtenerConsultaMedica(Integer idConsultaMedica);
    public Integer actualizarConsultaMedica(MedicalConsultation medicalConsultation);
    public Integer eliminarConsultaMedica(Integer idConsultaMedica);
}
