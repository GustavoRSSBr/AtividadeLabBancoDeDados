package com.bd.projob.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProjetoDto {
    private int codProjeto;
    private String titulo;
    private String descricao;
    private double remuneracao;
    private String emailPatrocinador;
    private String emailCandidato;
    private String statusProjeto;
}
