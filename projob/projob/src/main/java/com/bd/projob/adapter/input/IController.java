package com.bd.projob.adapter.input;

import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import org.springframework.http.ResponseEntity;

public interface IController {
    //------ Pessoas ------

    ResponseEntity<?> cadastrarPessoa(RequestPessoaDto requestPessoaDto);

    ResponseEntity<?> login(RequestLoginDto requestPessoaDto);

    ResponseEntity<?> atualizarPessoa(RequestPessoaDto requestPessoaDto);

    //------- Projeto ----

    ResponseEntity<?> cadastrarProjeto(RequestProjetoDto requestPessoaDto);

    ResponseEntity<?> atualizarProjeto(RequestProjetoDto requestPessoaDto, Integer idProjeto);

    ResponseEntity<?> deletarProjeto(Integer idProjeto);

    ResponseEntity<?> buscarProjeto(Integer idProjeto);
    ResponseEntity<?> listarProjetos();
    ResponseEntity<?> candidatar(Integer idProjeto);

    ResponseEntity<?> listarCandidatos(Integer idProjeto);

    ResponseEntity<?> aceitarCandidato(RequestAceiteDto requestPessoaDto);



}
