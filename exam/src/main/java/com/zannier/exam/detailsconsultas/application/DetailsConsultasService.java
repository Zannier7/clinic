package com.zannier.exam.detailsconsultas.application;

import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import com.zannier.exam.medico.domain.Medico;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DetailsConsultasService {

    public Collection<DetailsConsultas> obtenerDetailsConsultas();
    public DetailsConsultas registrarDetallesConsulta(DetailsConsultas detailsConsultas);
    public DetailsConsultas obtenerDetallesConsulta(Integer idDetailsConsultas);
    public Integer actualizarDetallesConsulta(DetailsConsultas detailsConsultas);
    public Integer eliminarDetallesConsulta(Integer idDetailsConsultas);

    public List<ConsultasPacienteResponse> consultasMedicasPorPaciente(@Param("dniPaciente") String dniPaciente);

    public List<DetailsConsultas> findDetallesConsultas(String dniPaciente);
}
