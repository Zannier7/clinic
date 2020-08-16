package com.zannier.exam.detailsconsultas.application;

import com.zannier.exam.detailsconsultas.domain.ConsultasPacienteResponse;
import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import com.zannier.exam.detailsconsultas.infraestructure.repository.DetailsConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DetailsConsultasServiceImpl implements DetailsConsultasService{

    @Autowired
    private DetailsConsultasRepository detailsConsultasRepository;

    @Override
    public Collection<DetailsConsultas> obtenerDetailsConsultas() {
        return (Collection<DetailsConsultas>) detailsConsultasRepository.findAll();
    }

    @Override
    public DetailsConsultas registrarDetallesConsulta(DetailsConsultas detailsConsultas) {
        return detailsConsultasRepository.save(detailsConsultas);
    }

    @Override
    public DetailsConsultas obtenerDetallesConsulta(Integer idDetailsConsultas) {
        return detailsConsultasRepository.findById(idDetailsConsultas).orElse(null);
    }

    @Override
    public Integer actualizarDetallesConsulta(DetailsConsultas detailsConsultas) {
        return detailsConsultasRepository.save(detailsConsultas) != null ? 1:0;
    }

    @Override
    public Integer eliminarDetallesConsulta(Integer idDetailsConsultas) {
        detailsConsultasRepository.deleteById(idDetailsConsultas);
        return 1;
    }

    @Override
    public List<ConsultasPacienteResponse> consultasMedicasPorPaciente(@Param("dniPaciente") String dniPaciente) {
        return detailsConsultasRepository.getConsultaMedicasPorPaciente(dniPaciente);
    }

    @Override
    public List<DetailsConsultas> findDetallesConsultas(String dniPaciente) {
        return detailsConsultasRepository.findDetallesConsultas(dniPaciente);
    }
}
