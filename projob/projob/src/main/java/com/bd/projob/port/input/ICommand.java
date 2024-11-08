package com.bd.projob.port.input;

import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.domain.entities.Pessoa;

import java.util.List;

public interface ICommand {
    Pessoa logar(RequestLoginDto requestLoginDto);

    void cadastrarPessoa(RequestPessoaDto requestPessoaDto);

    void atualizarPessoa(RequestPessoaDto requestPessoaDto, Pessoa pessoa);

    int cadastrarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa);

    void atualizarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa, Integer idProjeto);

    void deletarProjeto(Integer idProjeto, Pessoa pessoa);

    ResponseProjetoDto buscarProjeto(Integer idProjeto);

    List<ResponsePessoaDto> listarPessoas(Integer idProjeto);

    void aceitarCandidato(RequestAceiteDto requestPessoaDto, Pessoa pessoa);

    List<ResponseProjetoDto> listarProjetos();

    void candidatar(Pessoa pessoa, Integer idProjeto);
}
