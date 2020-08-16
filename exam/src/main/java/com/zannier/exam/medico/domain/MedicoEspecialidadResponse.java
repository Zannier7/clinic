package com.zannier.exam.medico.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MedicoEspecialidadResponse {

    private String firstName;
    private String lastName;
    private String dni;
    private String cmp;
    private String name;
}
