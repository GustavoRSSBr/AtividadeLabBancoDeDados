package com.bd.projob.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProjetoDto {
    private String titulo;
    private String descricao;
    private double remuneracao;
}
