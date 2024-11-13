package com.bd.projob.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Projeto {
    private int codProjeto;
    private String titulo;
    private int codPatroc;
    private String descricao;
    private double remuneracao;
    private String statusProj;
}
