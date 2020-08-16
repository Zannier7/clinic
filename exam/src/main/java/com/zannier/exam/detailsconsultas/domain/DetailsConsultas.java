package com.zannier.exam.detailsconsultas.domain;

import com.zannier.exam.consultas.domain.MedicalConsultation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_consultation")
public class DetailsConsultas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Integer id;

    @Column(name = "diagnostic")
    @NotBlank(message = "Escriba el diagnostico del paciente")
    private String diagnostic;

    @Column(name = "treatment")
    @NotBlank(message = "Escriba el tratamiento del paciente")
    private String treatment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medical_consultation")
    private MedicalConsultation medicalConsultation;
}
