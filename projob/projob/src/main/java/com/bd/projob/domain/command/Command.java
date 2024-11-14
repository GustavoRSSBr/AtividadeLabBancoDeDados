package com.bd.projob.domain.command;

import com.bd.projob.adapter.input.dto.request.RequestAceiteDto;
import com.bd.projob.adapter.input.dto.request.RequestLoginDto;
import com.bd.projob.adapter.input.dto.request.RequestPessoaDto;
import com.bd.projob.adapter.input.dto.request.RequestProjetoDto;
import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.config.exception.NegocioException;
import com.bd.projob.constantes.MensagemErro;
import com.bd.projob.domain.entities.Candidatura;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.domain.entities.Projeto;
import com.bd.projob.domain.enums.StatusProjeto;
import com.bd.projob.port.input.ICommand;
import com.bd.projob.port.output.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Command implements ICommand {
    private final IRepository repository;

    public Command(IRepository repository) {
        this.repository = repository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Pessoa logar(RequestLoginDto requestLoginDto) {

        LOGGER.info("Buscando informações do usuario pelo email");
        Pessoa pessoa = repository.buscarPessoaPeloEmail(requestLoginDto.getEmail());

        LOGGER.info("Verificando se existe o usuario");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.USUARIO_NAO_EXISTE.getMensagem());
        }

        LOGGER.info("Verificando credenciais");
        if (!passwordEncoder.matches(requestLoginDto.getSenha(),pessoa.getSenha())) {
            throw new NegocioException(MensagemErro.SENHA_INCORRETA.getMensagem());
        }

        return pessoa;
    }

    @Override
    public void cadastrarPessoa(RequestPessoaDto requestPessoaDto) {

        LOGGER.info("Validando telefone");
        if (requestPessoaDto.getTelefone().length() > 11) {
            throw new NegocioException(MensagemErro.TELEFONE_INVALIDO.getMensagem());
        }

        LOGGER.info("Validando se existe o telefone");
        if (repository.existeTelefone(requestPessoaDto.getTelefone())) {
            throw new NegocioException(MensagemErro.TELEFONE_INDISPONIVEL.getMensagem());
        }

        LOGGER.info("Validando email");
        if (!requestPessoaDto.getEmail().contains("@")) {
            throw new NegocioException(MensagemErro.EMAIL_INVALIDO.getMensagem());
        }

        if (repository.verificarEmailExiste(requestPessoaDto.getEmail())) {
            throw new NegocioException(MensagemErro.EMAIL_INVALIDO.getMensagem());
        }


        LOGGER.info("Validando senha");
        if ((requestPessoaDto.getSenha().length() < 8)) {
            throw new NegocioException(MensagemErro.SENHA_INVALIDA.getMensagem());
        }

        LOGGER.info("Criptografando senha");
        requestPessoaDto.setSenha(passwordEncoder.encode(requestPessoaDto.getSenha()));

        LOGGER.info("Persistindo usuario");
        repository.salvarPessoa(Pessoa.builder()
                .email(requestPessoaDto.getEmail())
                .senha(requestPessoaDto.getSenha())
                .nome(requestPessoaDto.getNome())
                .telefone(requestPessoaDto.getTelefone())
                .build());
    }

    @Override
    public void atualizarPessoa(RequestPessoaDto requestPessoaDto, Pessoa pessoa) {
        LOGGER.info("Vericando usuario logado");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        LOGGER.info("Validando telefone");
        if (requestPessoaDto.getTelefone().length() > 11) {
            throw new NegocioException(MensagemErro.TELEFONE_INVALIDO.getMensagem());
        }

        LOGGER.info("Validando se existe o telefone");
        if (repository.existeTelefone(requestPessoaDto.getTelefone())) {
            throw new NegocioException(MensagemErro.TELEFONE_INDISPONIVEL.getMensagem());
        }

        LOGGER.info("Validando email");
        if (!requestPessoaDto.getEmail().contains("@")) {
            throw new NegocioException(MensagemErro.EMAIL_INVALIDO.getMensagem());
        }

        if (repository.verificarEmailExiste(requestPessoaDto.getEmail())) {
            throw new NegocioException(MensagemErro.EMAIL_INVALIDO.getMensagem());
        }

        LOGGER.info("Validando senha");
        if ((requestPessoaDto.getSenha().length() < 8)) {
            throw new NegocioException(MensagemErro.SENHA_INVALIDA.getMensagem());
        }

        LOGGER.info("Criptografando nova senha");
        requestPessoaDto.setSenha(passwordEncoder.encode(requestPessoaDto.getSenha()));

        LOGGER.info("Persistindo usuario atualizado");
        repository.atualizarPessoa(Pessoa.builder()
                .codUsuario(pessoa.getCodUsuario())
                .email(requestPessoaDto.getEmail())
                .senha(requestPessoaDto.getSenha())
                .nome(requestPessoaDto.getNome())
                .telefone(requestPessoaDto.getTelefone())
                .build());
    }

    @Override
    public Integer cadastrarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa) {
        LOGGER.info("Verificando usuario logado");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        if(requestProjetoDto.getTitulo().length() > 50){
            throw new NegocioException(MensagemErro.TITULO_GRANDE.getMensagem());
        }

        LOGGER.info("Validando Remuneracao");
        if (requestProjetoDto.getRemuneracao() <= 0) {
            throw new NegocioException(MensagemErro.VALOR_MENOR_ZERO.getMensagem());
        }

        LOGGER.info("Construindo projeto");
        Projeto projeto = Projeto.builder()
                .titulo(requestProjetoDto.getTitulo())
                .remuneracao(requestProjetoDto.getRemuneracao())
                .descricao(requestProjetoDto.getDescricao())
                .codPatroc(pessoa.getCodUsuario())
                .statusProj(StatusProjeto.ABERTO.getMensagem())
                .build();

        LOGGER.info("Persistindo Projeto");
        return repository.cadastrarProjeto(projeto);
    }

    @Override
    public void atualizarProjeto(RequestProjetoDto requestProjetoDto, Pessoa pessoa, Integer idProjeto) {
        LOGGER.info("Verificando usuario logado");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        LOGGER.info("Verificando existencia do projeto");
        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException(MensagemErro.PROJETO_INEXISTENTE.getMensagem());
        }

        if(requestProjetoDto.getTitulo().length() > 50){
            throw new NegocioException(MensagemErro.TITULO_GRANDE.getMensagem());
        }

        LOGGER.info("Validando relação do usuario logado com o projeto a ser atualizado");
        if (!repository.verificarRelacaoPessoaProjeto(idProjeto, pessoa.getCodUsuario())) {
            throw new NegocioException(MensagemErro.SEM_PERMISSAO_PROJETO.getMensagem());
        }

        LOGGER.info("Validando remuneracao do projeto");
        if (requestProjetoDto.getRemuneracao() <= 0) {
            throw new NegocioException(MensagemErro.VALOR_MENOR_ZERO.getMensagem());
        }

        LOGGER.info("Construindo novo projeto");
        Projeto projeto = Projeto.builder()
                .codProjeto(idProjeto)
                .titulo(requestProjetoDto.getTitulo())
                .remuneracao(requestProjetoDto.getRemuneracao())
                .descricao(requestProjetoDto.getDescricao())
                .codPatroc(pessoa.getCodUsuario())
                .statusProj(StatusProjeto.ABERTO.getMensagem())
                .build();

        LOGGER.info("Atualizando projeto");
        repository.atualizarProjeto(projeto);
    }

    @Override
    public void deletarProjeto(Integer idProjeto, Pessoa pessoa) {
        LOGGER.info("Validando usuario logado");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        LOGGER.info("Validando relação do usuario logado com o projeto a ser atualizado");
        if (!repository.verificarRelacaoPessoaProjeto(idProjeto, pessoa.getCodUsuario())) {
            throw new NegocioException(MensagemErro.SEM_PERMISSAO_PROJETO.getMensagem());
        }

        LOGGER.info("Validando se o projeto existe");
        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException(MensagemErro.PROJETO_INEXISTENTE.getMensagem());
        }

        LOGGER.info("Deletando projeto");
        repository.deletarProjeto(idProjeto);
    }

    @Override
    public ResponseProjetoDto buscarProjeto(Integer idProjeto) {
        LOGGER.info("Buscando informações do projeto");
        ResponseProjetoDto responseProjetoDto = repository.buscarProjetoId(idProjeto);

        LOGGER.info("Validando se o projeto existe");
        if (responseProjetoDto == null) {
            throw new NegocioException(MensagemErro.PROJETO_INEXISTENTE.getMensagem());
        }

        LOGGER.info("Retornando projeto");
        return responseProjetoDto;
    }

    @Override
    public List<ResponsePessoaDto> listarPessoas(Integer idProjeto) {

        LOGGER.info("Validando existencia do projeto");
        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException(MensagemErro.PROJETO_INEXISTENTE.getMensagem());
        }

        LOGGER.info("Buscando candidatos do projeto");
        List<ResponsePessoaDto> pessoasCandidatadas = repository.listarPessoasCandidatadas(idProjeto);

        LOGGER.info("Vericando se existem pessoas no projeto");
        if (pessoasCandidatadas.isEmpty()) {
            throw new NegocioException(MensagemErro.SEM_CANDIDATURA.getMensagem());
        }

        LOGGER.info("Retornando pessoas");
        return pessoasCandidatadas;
    }

    @Override
    public void aceitarCandidato(RequestAceiteDto requestPessoaDto, Pessoa pessoa) {
        LOGGER.info("Vericando se pessoa existe");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        LOGGER.info("Vericando se existe relação da pessoa com o projeto");
        if (!repository.verificarRelacaoPessoaProjeto(requestPessoaDto.getIdProjeto(), pessoa.getCodUsuario())) {
            throw new NegocioException(MensagemErro.SEM_PERMISSAO_PROJETO.getMensagem());
        }

        LOGGER.info("Buscando informações da pessoa");
        Pessoa pessoaCandidata = repository.buscarPessoaPeloEmail(requestPessoaDto.getEmailPessoa());

        LOGGER.info("Vericando se pessoa existe");
        if (pessoaCandidata == null) {
            throw new NegocioException(MensagemErro.CANDIDATO_INEXISTENTE.getMensagem());
        }

        LOGGER.info("Aceitando candidatura");
        repository.aceitarCandidato(pessoaCandidata.getCodUsuario(), requestPessoaDto.getIdProjeto());

    }

    @Override
    public List<ResponseProjetoDto> listarProjetos() {
        LOGGER.info("Buscando projetos");
        List<ResponseProjetoDto> projetos = repository.listarProjetos();

        LOGGER.info("Vericando se os projetos existem");
        if (projetos.isEmpty()) {
            throw new NegocioException(MensagemErro.SEM_PROJETOS.getMensagem());
        }

        LOGGER.info("Retornando projeto");
        return projetos;
    }

    @Override
    public void candidatar(Pessoa pessoa, Integer idProjeto) {
        LOGGER.info("Validando usuario logado");
        if (pessoa == null) {
            throw new NegocioException(MensagemErro.PRECISA_LOGADO.getMensagem());
        }

        LOGGER.info("verificar se o projeto existe");
        if (!repository.verificarProjetoExiste(idProjeto)) {
            throw new NegocioException(MensagemErro.PROJETO_INEXISTENTE.getMensagem());
        }

        LOGGER.info("verificar se o projeto está aberto");
        if (!repository.verificarProjetoAberto(idProjeto)) {
            throw new NegocioException(MensagemErro.PROJETO_FECHADO.getMensagem());
        }

        LOGGER.info("verificar se já está candidatado");
        if (repository.verificarUsuarioCandidatado(pessoa.getCodUsuario(), idProjeto)) {
            throw new NegocioException(MensagemErro.USUARIO_JA_CANDIDATADO.getMensagem());
        }

        LOGGER.info("persistindo candidatura do usuario");
        repository.candidatar(Candidatura.builder()
                .codCandidato(pessoa.getCodUsuario())
                .codProj(idProjeto)
                .build());

    }
}
