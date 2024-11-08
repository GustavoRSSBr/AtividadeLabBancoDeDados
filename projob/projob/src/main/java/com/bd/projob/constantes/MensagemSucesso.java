package com.bd.projob.constantes;

public enum MensagemSucesso {

    PESSOA_CADASTRADA("Pessoa cadastrada com sucesso!"),
    PESSOA_LOGADA("Login Realizado com sucesso!"),
    PESSOA_ATUALIZADA("Pessoa atualizada com sucesso!"),
    PROJETO_CADASTRADO("Projeto cadastrado com sucesso!"),
    PROJETO_ATUALIZADO("Projeto atualizado com sucesso!"),
    PROJETO_DELETADO("Projeto deletado com sucesso!"),
    PROJETO_ENCONTRADO("Projeto encontrado com sucesso!"),
    CANDIDATO_ENCONTRADO("Cadidatos encontrados com sucesso!"),
    CANDIDATO_ACEITO("Candidato aceito no projeto!"),
    PROJETOS_ENCONTRADOS("Projetos encontrado com sucesso!"),
    CANDIDATURA_REALIZADA("Candidatura realizada com sucesso!")
    ;


    private final String mensagem;

    MensagemSucesso(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
