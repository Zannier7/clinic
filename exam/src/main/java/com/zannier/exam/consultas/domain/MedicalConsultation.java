package com.zannier.exam.consultas.domain;

import com.zannier.exam.detailsconsultas.domain.DetailsConsultas;
import com.zannier.exam.medico.domain.Medico;
import com.zannier.exam.paciente.domain.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "detailsConsultas")
@Entity
@Table(name = "medical_consultation")
public class MedicalConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Integer id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "create_date")
    @NotNull(message = "Ingrese la fecha")
    @FutureOrPresent(message = "No puede ingresar una fecha pasada. Ingrese una fecha actual o futura")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doctor", updatable = false)
    private Medico medicos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private Paciente pacientes;

    @OneToMany(mappedBy = "medicalConsultation", cascade = CascadeType.ALL)
    private Collection<DetailsConsultas> detailsConsultas;
}
