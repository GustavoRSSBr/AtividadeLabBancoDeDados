package com.bd.projob.adapter.output;

import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.domain.entities.Candidatura;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.domain.entities.Projeto;
import com.bd.projob.port.output.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Repository implements IRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Pessoa buscarPessoaPeloEmail(String email) {
        return null;
    }

    @Override
    public void salvarPessoa(Pessoa pessoa) {

    }

    @Override
    public void atualizarPessoa(Pessoa pessoa) {

    }

    @Override
    public int cadastrarProjeto(Projeto projeto) {

        return 0;
    }

    @Override
    public boolean verificarProjetoExiste(Integer idProjeto) {
        return false;
    }

    @Override
    public void atualizarProjeto(Projeto projeto) {

    }

    @Override
    public boolean verificarRelacaoPessoaProjeto(Integer idProjeto, int codUsuario) {
        return false;
    }

    @Override
    public void deletarProjeto(Integer idProjeto) {

    }

    @Override
    public ResponseProjetoDto buscarProjetoId(Integer idProjeto) {
        return null;
    }

    @Override
    public List<ResponsePessoaDto> listarPessoasCandidatadas(Integer idProjeto) {
        return null;
    }

    @Override
    public List<ResponseProjetoDto> listarProjetos() {
        return null;
    }

    @Override
    public void candidatar(Candidatura build) {

    }

    @Override
    public void aceitarCandidato(int codUsuario, Integer idProjeto) {

    }

    @Override
    public boolean existeTelefone(String telefone) {
        return false;
    }
}
