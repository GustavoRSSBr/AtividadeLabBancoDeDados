package com.bd.projob.adapter.input;

import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import com.bd.projob.adapter.input.dto.response.ResponseDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.constantes.MensagemSucesso;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.port.input.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller implements IController {
    private Pessoa pessoa;

    @Autowired
    ICommand command;

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/cadastrar-pessoa")
    @Override
    public ResponseEntity<?> cadastrarPessoa(@RequestBody RequestPessoaDto requestPessoaDto) {
        LOGGER.info("Início do método cadastrarPessoa");
        long startTime = System.currentTimeMillis();

        command.cadastrarPessoa(requestPessoaDto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PESSOA_CADASTRADA.getMensagem()).build());
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
        LOGGER.info("Início do método login");
        long startTime = System.currentTimeMillis();

        pessoa = command.logar(requestLoginDto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PESSOA_LOGADA.getMensagem()).build());
    }

    @PostMapping("/atualizar-pessoa")
    @Override
    public ResponseEntity<?> atualizarPessoa(@RequestBody RequestPessoaDto requestPessoaDto) {
        LOGGER.info("Início do método atualizarPessoa");
        long startTime = System.currentTimeMillis();

        command.atualizarPessoa(requestPessoaDto, pessoa);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PESSOA_ATUALIZADA.getMensagem()).build());
    }

    @PostMapping("/cadastrar-projeto")
    @Override
    public ResponseEntity<?> cadastrarProjeto(@RequestBody RequestProjetoDto requestProjetoDto) {
        LOGGER.info("Início do método cadastrarProjeto");
        long startTime = System.currentTimeMillis();

        Integer idProjeto = command.cadastrarProjeto(requestProjetoDto, pessoa);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PROJETO_CADASTRADO.getMensagem()).dado(idProjeto).build());
    }

    @PostMapping("/atualizar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> atualizarProjeto(@RequestBody RequestProjetoDto requestProjetoDto, @PathVariable Integer idProjeto) {
        LOGGER.info("Início do método atualizarProjeto");
        long startTime = System.currentTimeMillis();

        command.atualizarProjeto(requestProjetoDto, pessoa, idProjeto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PROJETO_ATUALIZADO.getMensagem()).build());
    }

    @DeleteMapping("/deletar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> deletarProjeto(@PathVariable Integer idProjeto) {
        LOGGER.info("Início do método deletarProjeto");
        long startTime = System.currentTimeMillis();

        command.deletarProjeto(idProjeto, pessoa);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PROJETO_DELETADO.getMensagem()).build());
    }

    @GetMapping("/buscar-projeto/{idProjeto}")
    @Override
    public ResponseEntity<?> buscarProjeto(@PathVariable Integer idProjeto) {
        LOGGER.info("Início do método buscarProjeto");
        long startTime = System.currentTimeMillis();

        ResponseProjetoDto projeto = command.buscarProjeto(idProjeto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PROJETO_ENCONTRADO.getMensagem()).dado(projeto).build());
    }

    @GetMapping("/listar-candidatos/{idProjeto}")
    @Override
    public ResponseEntity<?> listarCandidatos(@PathVariable Integer idProjeto) {
        LOGGER.info("Início do método listarCandidatos");
        long startTime = System.currentTimeMillis();

        List<ResponsePessoaDto> cadidatos = command.listarPessoas(idProjeto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.CANDIDATO_ENCONTRADO.getMensagem()).dado(cadidatos).build());
    }

    @PostMapping("/aceitar-canditado")
    @Override
    public ResponseEntity<?> aceitarCandidato(@RequestBody RequestAceiteDto requestPessoaDto) {
        LOGGER.info("Início do método aceitarCandidato");
        long startTime = System.currentTimeMillis();

        command.aceitarCandidato(requestPessoaDto, pessoa);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.CANDIDATO_ACEITO.getMensagem()).build());
    }

    @GetMapping("/listar-projetos")
    @Override
    public ResponseEntity<?> listarProjetos() {
        LOGGER.info("Início do método listarProjetos");
        long startTime = System.currentTimeMillis();

        List<ResponseProjetoDto> projetos = command.listarProjetos();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.PROJETOS_ENCONTRADOS.getMensagem()).dado(projetos).build());
    }

    @PostMapping("/candidatar/{idProjeto}")
    @Override
    public ResponseEntity<?> candidatar(@PathVariable Integer idProjeto) {
        LOGGER.info("Início do método candidatar");
        long startTime = System.currentTimeMillis();

        command.candidatar(pessoa, idProjeto);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");

        return ResponseEntity.ok(ResponseDto.builder().mensagem(MensagemSucesso.CANDIDATURA_REALIZADA.getMensagem()).build());
    }
}
