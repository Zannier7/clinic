package com.zannier.exam.detailsconsultas.infraestructure.repository;

import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsConsultasRepository extends CrudRepository<DetailsConsultas, Integer> {

    @Modifying
    @Query("SELECT new com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse(p.firstName, p.lastName,dc.diagnostic,dc.treatment,mc.createDate) FROM Paciente p, DetailsConsultas dc, MedicalConsultation mc " +
            "WHERE mc.id = dc.medicalConsultation.id AND p.id =mc.pacientes.id  AND p.dni=:dniPaciente")
    List<ConsultasPacienteResponse> getConsultaMedicasPorPaciente(@Param("dniPaciente") String dniPaciente);

    @Modifying
    @Query("SELECT dc FROM DetailsConsultas dc, MedicalConsultation mc, Paciente p WHERE p.id = mc.pacientes.id AND mc.id = dc.medicalConsultation.id AND p.dni =:dniPaciente")
    List<DetailsConsultas> findDetallesConsultas(@Param("dniPaciente") String dniPaciente);
}
