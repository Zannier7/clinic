package com.zannier.exam.paciente.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zannier.exam.consultas.domain.MedicalConsultation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "medicalConsultations")
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "serial")
    private Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "Ingrese el NOMBRE del PACIENTE")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Ingrese el APELLIDO del PACIENTE")
    private String lastName;

    @Column(name = "dni")
    @NotBlank(message = "Ingrese el DNI del PACIENTE")
    private String dni;

    @Column(name = "numero_historia_clinica")
    @NotBlank(message = "Ingrese el NUMERO DE HISTORIA del PACIENTE")
    private String numeroHistoriaClinica;

    @OneToMany(mappedBy = "pacientes", cascade = CascadeType.ALL)
    private Collection<MedicalConsultation> medicalConsultations;
}
