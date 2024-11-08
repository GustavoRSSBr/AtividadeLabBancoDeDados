package com.bd.projob.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseProjetoDto {
    private int codigoProjeto;
    private String titulo;
    private String descricao;
    private double remuneracao;
    private String emailPatrocinador;
    private String statusProjeto;
}
