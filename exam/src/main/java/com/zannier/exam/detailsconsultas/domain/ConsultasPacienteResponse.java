package com.zannier.exam.detailsconsultas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsultasPacienteResponse {

    private String firstName;
    private String lastName;
    private String diagnostic;
    private String treatment;
    private LocalDate createDate;

}
