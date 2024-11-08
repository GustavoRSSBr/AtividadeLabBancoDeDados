package com.bd.projob.domain.enums;

import lombok.Getter;


@Getter
public enum StatusProjeto {
    ABERTO("ABERTO"),
    FECHADO("FECHADO");

    private final String mensagem;

    StatusProjeto(String mensagem) {
        this.mensagem = mensagem;
    }
}
