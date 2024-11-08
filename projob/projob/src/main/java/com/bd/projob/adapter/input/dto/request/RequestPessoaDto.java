package com.bd.projob.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPessoaDto {
    private String nome;
    private String email;
    private String telefone;
    private String senha;
}