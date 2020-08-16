package com.zannier.exam.paciente.infraestructure.repository;

import com.zannier.exam.paciente.domain.Paciente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Integer> {

    @Query("SELECT p FROM Paciente p WHERE p.dni=:dniPaciente")
    Paciente findByDocumento(@Param("dniPaciente")String dniPaciente);
}
