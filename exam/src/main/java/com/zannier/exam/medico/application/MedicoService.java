package com.zannier.exam.medico.application;

import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.medico.domain.MedicoEspecialidadResponse;

import java.util.Collection;


public interface MedicoService {

    public Collection<Medico> obtenerMedicos();
    public Medico registrarMedico(Medico medico);
    public Medico obtenerMedico(Integer idMedico);
    public Integer actualizarMedico(Medico medico);
    public Integer eliminarMedico(Integer idMedico);
    public Collection<Medico> obtenerMedicoPorEspecialidad(Integer idEspecialidad);
    public Collection<MedicoEspecialidadResponse> obtenerMedicoPorEspecialidadReport(Integer idEspecialidad);
    public Medico obtenerPorDocumento(String documento);
    public Integer updatePasswordMedico(Integer idMedico,String nuevaClave);
    public Integer updateEspecialidadMedico(Integer idMedico,Integer nuevaEspecialidad);
}
