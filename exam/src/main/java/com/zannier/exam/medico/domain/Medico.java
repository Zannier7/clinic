package com.zannier.exam.medico.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zannier.exam.consultas.domain.MedicalConsultation;
import com.zannier.exam.especialidad.domain.Especialidad;
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
@ToString(exclude = "medicalConsultation")
@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "Ingrese el NOMBRE del MEDICO")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Ingrese el APELLIDO del MEDICO")
    private String lastName;

    @Column(name = "dni")
    @NotBlank(message = "Ingrese el DNI del MEDICO")
    private String dni;

    @Column(name = "cmp")
    @NotBlank(message = "Ingrese el CMP del MEDICO")
    private String cmp;

    @Column(name = "clave")
    @NotBlank(message = "Ingrese la contraseña del MEDICO")
    private String clave;

    @Column(name = "rol")
    @NotBlank(message = "Selecciona el ROL del MEDICO")
    private String rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_specialty", updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Especialidad especialidad;

    @OneToMany(mappedBy = "medicos", cascade = CascadeType.ALL)
    private Collection<MedicalConsultation> medicalConsultation;

}
