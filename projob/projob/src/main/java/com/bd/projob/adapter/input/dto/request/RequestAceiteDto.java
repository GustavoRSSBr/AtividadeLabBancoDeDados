package com.bd.projob.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAceiteDto {
    private String emailPessoa;
    private Integer idProjeto;
}
