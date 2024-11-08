package com.bd.projob.domain.command;

import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.config.exception.NegocioException;
import com.bd.projob.domain.entities.Candidatura;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.domain.entities.Projeto;
import com.bd.projob.domain.enums.StatusProjeto;
import com.bd.projob.port.input.ICommand;
import com.bd.projob.port.output.IRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Command implements ICommand {
    private final IRepository repository;

    public Command(IRepository repository) {
        this.repository = repository;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Pessoa logar(RequestLoginDto requestLoginDto) {
        Pessoa pessoa = repository.buscarPessoaPeloEmail(requestLoginDto.getEmail());
        if (pessoa == null) {
            throw new NegocioException("Usuario não existe!");
        }

        if (!passwordEncoder.matches(pessoa.getSenha(), requestLoginDto.getSenha())) {
            throw new NegocioException("Senha incorreta!");
        }

        return pessoa;
    }

    @Override
    public void cadastrarPessoa(RequestPessoaDto requestPessoaDto) {
        if (requestPessoaDto.getTelefone().length() > 11) {
            throw new NegocioException("Número de telefone invalido");
        }

        if (!requestPessoaDto.getEmail().contains("@")) {
            throw new NegocioException("Email inválido");
        }

        if (!(requestPessoaDto.getSenha().length() < 8)) {
            throw new NegocioException("Senha inválida");
        }

        requestPessoaDto.setSenha(passwordEncoder.encode(requestPessoaDto.getSenha()));

        repository.salvarPessoa(Pessoa.builder()
                .email(requestPessoaDto.getEmail())
                .senha(requestPessoaDto.getSenha())
                .nome(requestPessoaDto.getNome())
                .telefone(requestPessoaDto.getTelefone())
                .build());
    }

    @Override
    public void atualizarPessoa(RequestPessoaDto requestPessoaDto) {
        if (requestPessoaDto.getTelefone().length() > 11) {
            throw new NegocioException("Número de telefone invalido");
        }

        if (!requestPessoaDto.getEmail().contains("@")) {
            throw new NegocioException("Email inválido");
        }

        if (!(requestPessoaDto.getSenha().length() < 8)) {
            throw new NegocioException("Senha inválida");
        }

        requestPessoaDto.setSenha(passwordEncoder.encode(requestPessoaDto.getSenha()));

        repository.atualizarPessoa(Pessoa.builder()
                .email(requestPessoaDto.getEmail())
                .senha(requestPessoaDto.getSenha())
                .nome(requestPessoaDto.getNome())
                .telefone(requestPessoaDto.getTelefone())
                .build());
    }

    @Override
    public void cadastrarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa) {
        if (pessoa == null) {
            throw new NegocioException("Você precisa estar logado para realizar essa operação!");
        }

        if (requestProjetoDto.getRemuneracao() <= 0) {
            throw new NegocioException("O valor não pode ser menor ou igual a 0");
        }

        Projeto projeto = Projeto.builder()
                .titulo(requestProjetoDto.getTitulo())
                .remuneracao(requestProjetoDto.getRemuneracao())
                .descricao(requestProjetoDto.getDescricao())
                .codPatroc(pessoa.getCodUsuario())
                .status_proj(StatusProjeto.ABERTO.getMensagem())
                .build();

        repository.cadastrarProjeto(projeto);
    }

    @Override
    public void atualizarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa, Integer idProjeto) {
        if (pessoa == null) {
            throw new NegocioException("Você precisa estar logado para realizar essa operação!");
        }

        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException("O projeto não existe");
        }

        if (repository.verificarRelacaoPessoaProjeto(idProjeto, pessoa.getCodUsuario())) {
            throw new NegocioException("A pessoa logada não tem permissão para acessar esse projeto");
        }

        if (requestProjetoDto.getRemuneracao() <= 0) {
            throw new NegocioException("O valor não pode ser menor ou igual a 0");
        }

        Projeto projeto = Projeto.builder()
                .codProjeto(idProjeto)
                .titulo(requestProjetoDto.getTitulo())
                .remuneracao(requestProjetoDto.getRemuneracao())
                .descricao(requestProjetoDto.getDescricao())
                .codPatroc(pessoa.getCodUsuario())
                .status_proj(StatusProjeto.ABERTO.getMensagem())
                .build();

        repository.atualizarProjeto(projeto);
    }

    @Override
    public void deletarProjeto(Integer idProjeto, Pessoa pessoa) {
        if (pessoa == null) {
            throw new NegocioException("Você precisa estar logado para realizar essa operação!");
        }

        if (repository.verificarRelacaoPessoaProjeto(idProjeto, pessoa.getCodUsuario())) {
            throw new NegocioException("A pessoa logada não tem permissão para acessar esse projeto");
        }

        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException("O projeto não existe");
        }

        repository.deletarProjeto(idProjeto);
    }

    @Override
    public ResponseProjetoDto buscarProjeto(Integer idProjeto) {
        ResponseProjetoDto responseProjetoDto = repository.buscarProjetoId(idProjeto);

        if (responseProjetoDto == null) {
            throw new NegocioException("O projeto não existe");
        }

        return responseProjetoDto;
    }

    @Override
    public List<ResponsePessoaDto> listarPessoas(Integer idProjeto) {
        List<ResponsePessoaDto> pessoasCandidatadas = repository.listarPessoasCandidatadas(idProjeto);

        if (pessoasCandidatadas.isEmpty()) {
            throw new NegocioException("Nenhuma pessoa se canditou a esse projeto ainda!");
        }

        return pessoasCandidatadas;
    }

    @Override
    public void aceitarCandidato(RequestAceiteDto requestPessoaDto, Pessoa pessoa) {
        if (pessoa == null) {
            throw new NegocioException("Você precisa estar logado para realizar essa operação!");
        }

        if (repository.verificarRelacaoPessoaProjeto(requestPessoaDto.getIdProjeto(), pessoa.getCodUsuario())) {
            throw new NegocioException("A pessoa logada não tem permissão para acessar esse projeto");
        }

        Pessoa pessoaCandidata = repository.buscarPessoaPeloEmail(requestPessoaDto.getEmailPessoa());
        if (pessoaCandidata == null) {
            throw new NegocioException("O candidato não existe");
        }

        repository.aceitarCandidato(pessoaCandidata.getCodUsuario(), requestPessoaDto.getIdProjeto());

    }

    @Override
    public List<ResponseProjetoDto> listarProjetos() {
        List<ResponseProjetoDto> projetos = repository.listarProjetos();

        if (projetos.isEmpty()) {
            throw new NegocioException("Não existe projetos cadastrados!");
        }

        return projetos;
    }

    @Override
    public void candidatar(Pessoa pessoa, Integer idProjeto) {
        if (pessoa == null) {
            throw new NegocioException("Você precisa estar logado para realizar essa operação!");
        }

        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException("O projeto não existe");
        }

        repository.candidatar(Candidatura.builder()
                .codCandidato(pessoa.getCodUsuario())
                .codProj(idProjeto)
                .build());

    }


}
