package com.bd.projob.constantes;

public enum MensagemErro {
    GENERIC_ERROR("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde."),
    USUARIO_NAO_EXISTE("Usuario não existe!"),
    SENHA_INCORRETA("Senha incorreta!"),
    TELEFONE_INVALIDO("Número de telefone invalido"),
    EMAIL_INVALIDO("Email inválido"),
    SENHA_INVALIDA("Senha inválida, ela deve ter 8 ou mais caracteres!"),
    PRECISA_LOGADO("Você precisa estar logado para realizar essa operação!"),
    PROJETO_INEXISTENTE("O projeto não existe"),
    VALOR_MENOR_ZERO("O valor não pode ser menor ou igual a 0"),
    SEM_PERMISSAO_PROJETO("A pessoa logada não tem permissão para acessar esse projeto"),
    SEM_CANDIDATURA("Nenhuma pessoa se candidatou a esse projeto ainda!"),
    CANDIDATO_INEXISTENTE("O candidato não existe"),
    SEM_PROJETOS("Não existe projetos cadastrados!"),
    TELEFONE_INDISPONIVEL("Esse telefone já está cadastrado");

    private final String mensagem;

    MensagemErro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
