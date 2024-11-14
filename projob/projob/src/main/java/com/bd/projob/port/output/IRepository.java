package com.bd.projob.port.output;

import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.domain.entities.Candidatura;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.domain.entities.Projeto;

import java.util.List;

public interface IRepository {
    
    Pessoa buscarPessoaPeloEmail(String email);

    void salvarPessoa(Pessoa pessoa);

    void atualizarPessoa(Pessoa pessoa);

    Integer cadastrarProjeto(Projeto projeto);

    boolean verificarProjetoExiste(Integer idProjeto);

    void atualizarProjeto(Projeto projeto);

    boolean verificarRelacaoPessoaProjeto(Integer idProjeto, int codUsuario);

    void deletarProjeto(Integer idProjeto);

    ResponseProjetoDto buscarProjetoId(Integer idProjeto);

    List<ResponsePessoaDto> listarPessoasCandidatadas(Integer idProjeto);

    List<ResponseProjetoDto> listarProjetos();

    void candidatar(Candidatura candidatura);

    void aceitarCandidato(int codUsuario, Integer idProjeto);

    boolean existeTelefone(String telefone);

    boolean verificarEmailExiste(String email);

    boolean verificarProjetoAberto(Integer idProjeto);

    boolean verificarUsuarioCandidatado(int codUsuario, Integer idProjeto);
}
