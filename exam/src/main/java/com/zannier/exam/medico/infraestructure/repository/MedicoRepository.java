package com.zannier.exam.medico.infraestructure.repository;

import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.medico.domain.MedicoEspecialidadResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Integer> {

    @Modifying
    @Query("SELECT m FROM Especialidad e, Medico m WHERE e.id = m.especialidad.id AND e.id=:idEspecialidad")
    Collection<Medico> findMedicoByEspecialidad(@Param("idEspecialidad") Integer idEspecialidad);

    @Modifying
    @Query("SELECT new com.zannier.exam.medico.domain.MedicoEspecialidadResponse(m.firstName,m.lastName,m.dni,m.cmp,e.name) FROM Especialidad e, Medico m WHERE e.id = m.especialidad.id AND e.id=:idEspecialidad")
    Collection<MedicoEspecialidadResponse> findMedicoByEspecialidadReport(@Param("idEspecialidad") Integer idEspecialidad);

    @Query("SELECT m FROM Medico m WHERE m.dni=:dniMedico")
    Medico findByDocumento(@Param("dniMedico") String dniMedico);

    @Transactional
    @Modifying
    @Query("UPDATE Medico m SET m.clave=:nuevaClave WHERE m.id=:idMedico")
    Integer updatePasswordMedico(@Param("idMedico")Integer idMedico,@Param("nuevaClave")String nuevaClave);

    @Transactional
    @Modifying
    @Query("UPDATE Medico m SET m.especialidad.id =:nuevaEspecialidad WHERE m.id=:idMedico")
    Integer updateEspecialidadMedico(@Param("idMedico")Integer idMedico,@Param("nuevaEspecialidad")Integer nuevaEspecialidad);

}
