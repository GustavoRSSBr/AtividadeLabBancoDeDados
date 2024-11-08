package com.bd.projob.adapter.input;

import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import com.bd.projob.adapter.input.dto.response.ResponseDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.port.input.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class Controller implements IController {
    private Pessoa pessoa;

    @Autowired
    ICommand command;

    @PostMapping("/cadastrar-pessoa")
    @Override
    public ResponseEntity<?> cadastrarPessoa(@RequestBody RequestPessoaDto requestPessoaDto) {
        command.cadastrarPessoa(requestPessoaDto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Pessoa cadastrada com sucesso!"));
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
        pessoa = command.logar(requestLoginDto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Login Realizado com sucesso!"));
    }

    @PostMapping("/atualizar-pessoa")
    @Override
    public ResponseEntity<?> atualizarPessoa(@RequestBody RequestPessoaDto requestPessoaDto) {
        command.atualizarPessoa(requestPessoaDto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Pessoa atualizada com sucesso!"));
    }

    @PostMapping("/cadastrar-projeto")
    @Override
    public ResponseEntity<?> cadastrarProjeto(@RequestBody RequestProjetoDto requestProjetoDto) {
        command.cadastrarProjeto(requestProjetoDto, pessoa);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Projeto atualizado com sucesso!"));
    }

    @PostMapping("/atualizar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> atualizarProjeto(@RequestBody RequestProjetoDto requestProjetoDto, @PathVariable Integer idProjeto) {
        command.atualizarProjeto(requestProjetoDto, pessoa, idProjeto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Projeto atualizado com sucesso!"));
    }

    @DeleteMapping("/deletar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> deletarProjeto(@PathVariable Integer idProjeto) {
        command.deletarProjeto(idProjeto, pessoa);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Projeto deletado com sucesso!"));
    }

    @GetMapping("/buscar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> buscarProjeto(@PathVariable Integer idProjeto) {
        ResponseProjetoDto projeto = command.buscarProjeto(idProjeto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Projeto encontrado com sucesso!").dado(projeto));
    }

    @GetMapping("/listar-candidatos/{idProjeto}")
    @Override
    public ResponseEntity<?> listarCandidatos(@PathVariable Integer idProjeto) {
        List<ResponsePessoaDto> cadidatos = command.listarPessoas(idProjeto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Cadidatos encontrados com sucesso!").dado(cadidatos));
    }

    @PostMapping("/aceitar-canditado")
    @Override
    public ResponseEntity<?> aceitarCandidato(@RequestBody RequestAceiteDto requestPessoaDto) {
        command.aceitarCandidato(requestPessoaDto, pessoa);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Candidato aceito no projeto!"));
    }

    @GetMapping("/listar-projetos")
    @Override
    public ResponseEntity<?> listarProjetos() {
        List<ResponseProjetoDto> projetos = command.listarProjetos();
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Projetos encontrado com sucesso!").dado(projetos));
    }

    @PostMapping("/candidatar/{idProjeto}")
    @Override
    public ResponseEntity<?> candidatar(@PathVariable Integer idProjeto) {
        command.candidatar(pessoa, idProjeto);
        return ResponseEntity.ok(ResponseDto.builder().mensagem("Candidatura realizada com sucesso!"));
    }
}
