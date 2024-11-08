package com.bd.projob.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pessoa {
    private int codUsuario;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
}
