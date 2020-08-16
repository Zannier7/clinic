package com.zannier.exam.consultas.infraestructure.repository;

import com.zannier.exam.consultas.domain.MedicalConsultation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalConsultationRepository extends CrudRepository<MedicalConsultation, Integer> {

}
