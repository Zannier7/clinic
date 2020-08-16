package com.zannier.exam.especialidad.infraestructure.repository;

import com.zannier.exam.especialidad.domain.Especialidad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EspecialidadRepository extends CrudRepository<Especialidad, Integer> {

}
