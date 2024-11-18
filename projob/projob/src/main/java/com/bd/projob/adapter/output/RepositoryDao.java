package com.bd.projob.adapter.output;

import com.bd.projob.adapter.input.dto.response.ResponsePessoaDto;
import com.bd.projob.adapter.input.dto.response.ResponseProjetoDto;
import com.bd.projob.domain.entities.Candidatura;
import com.bd.projob.domain.entities.Pessoa;
import com.bd.projob.domain.entities.Projeto;
import com.bd.projob.port.output.IRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryDao implements IRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Pessoa buscarPessoaPeloEmail(String email) {
        String sql = "SELECT codUsuario, nome, email, telefone, senha FROM usuario WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pessoa.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void salvarPessoa(Pessoa pessoa) {
        String sql = "INSERT INTO usuario (nome, email, telefone, senha) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getSenha());
    }

    @Override
    public void atualizarPessoa(Pessoa pessoa) {
        String sql = "update usuario set nome = ?, email = ?, telefone = ?, senha = ? where codUsuario = ?;";
        jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getSenha(), pessoa.getCodUsuario());
    }

    @Transactional
    @Override
    public Integer cadastrarProjeto(Projeto projeto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("cadastrarProjeto")
                .declareParameters(
                        new SqlParameter("titulo_in", Types.VARCHAR),
                        new SqlParameter("codPatroc_in", Types.INTEGER),
                        new SqlParameter("descricao_in", Types.LONGVARCHAR),
                        new SqlParameter("remuneracao_in", Types.DECIMAL),
                        new SqlParameter("status_proj_in", Types.VARCHAR),
                        new SqlOutParameter("codProjeto_out", Types.INTEGER)
                );

        Map<String, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put("titulo_in", projeto.getTitulo());
        parametrosEntrada.put("codPatroc_in", projeto.getCodPatroc());
        parametrosEntrada.put("descricao_in", projeto.getDescricao());
        parametrosEntrada.put("remuneracao_in", projeto.getRemuneracao());
        parametrosEntrada.put("status_proj_in", projeto.getStatusProj());

        Map<String, Object> ParametroSaida = jdbcCall.execute(parametrosEntrada);

        return (Integer) ParametroSaida.get("codProjeto_out");
    }

    @Override
    public boolean verificarProjetoExiste(Integer idProjeto) {
        String sql = "select exists (select True from projeto where codProjeto = ?) AS existe;";

        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, idProjeto);

        return existe != null && existe;
    }

    @Override
    public void atualizarProjeto(Projeto projeto) {
        String sql = """
            UPDATE projeto SET titulo = ?, codPatroc = ?, descricao = ?, remuneracao = ?, status_proj = ?
            WHERE codProjeto = ?;""";

        jdbcTemplate.update(sql, projeto.getTitulo(), projeto.getCodPatroc(), projeto.getDescricao(),
                projeto.getRemuneracao(), projeto.getStatusProj(), projeto.getCodProjeto());
    }


    @Override
    public boolean verificarRelacaoPessoaProjeto(Integer idProjeto, int codUsuario) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projeto WHERE codProjeto = ? AND codPatroc = ?) AS existe";

        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, idProjeto, codUsuario);

        return existe != null && existe;
    }


    @Override
    public void deletarProjeto(Integer idProjeto) {
        String sql = "DELETE FROM projeto WHERE codProjeto = ?;";

        jdbcTemplate.update(sql, idProjeto);
    }


    @Override
    public ResponseProjetoDto buscarProjetoId(Integer idProjeto) {
        String sql = """
            SELECT\s
                p.codProjeto,
                p.titulo,
                p.descricao,
                p.remuneracao,
                u.email AS emailPatrocinador,
                c.email AS emailCandidato,
                p.status_proj AS statusProjeto
            FROM projeto p
            INNER JOIN usuario u ON p.codPatroc = u.codUsuario
            LEFT JOIN usuario c ON p.codCandidato = c.codUsuario
            WHERE p.codProjeto = ?""";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(ResponseProjetoDto.class),
                    idProjeto
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ResponsePessoaDto> listarPessoasCandidatadas(Integer idProjeto) {
        String sql = """
            SELECT u.nome, u.email, u.telefone
            FROM candidatura c
            INNER JOIN projeto p ON c.codProj = p.codProjeto
            INNER JOIN usuario u ON c.codCandidato = u.codUsuario
            WHERE c.codProj = ?;""";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ResponsePessoaDto.class), idProjeto);
    }


    @Override
    public List<ResponseProjetoDto> listarProjetos() {
        String sql = """
            SELECT p.codProjeto, p.titulo, p.descricao, p.remuneracao, u.email AS emailPatrocinador, p.status_proj AS statusProjeto
            FROM projeto p
            INNER JOIN usuario u ON p.codPatroc = u.codUsuario;
            """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ResponseProjetoDto.class));
    }


    @Override
    public void candidatar(Candidatura candidatura) {
        String sql = "insert into candidatura (codProj, codCandidato) values (?, ?);";

        jdbcTemplate.update(sql, candidatura.getCodProj(), candidatura.getCodCandidato());
    }

    @Override
    public void aceitarCandidato(int codUsuario, Integer idProjeto) {
        String sql = "update projeto set codCandidato = ?, status_proj = \"FECHADO\" where codProjeto = ?;";

        jdbcTemplate.update(sql, codUsuario, idProjeto);
    }

    @Override
    public boolean existeTelefone(String telefone) {
        String sql = """
            SELECT EXISTS (
            SELECT TRUE FROM usuario
            WHERE telefone = ?
            ) AS existe;""";

        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, telefone);

        return existe != null && existe;
    }

    @Override
    public boolean verificarEmailExiste(String email) {
        String sql = """
            SELECT EXISTS (
            SELECT TRUE FROM usuario
            WHERE email = ?
            ) AS existe;""";

        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, email);

        return existe != null && existe;
    }

    @Override
    public boolean verificarProjetoAberto(Integer idProjeto) {
        String sql = """
                select exists (
                	select True from projeto
                	where status_proj = "ABERTO"
                	and codProjeto = ?
                ) AS existe;
                """;
        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, idProjeto);

        return existe != null && existe;
    }

    @Override
    public boolean verificarUsuarioCandidatado(int codUsuario, Integer idProjeto) {
        String sql = """
                select exists (
                	select True from candidatura
                	where codCandidato = ?
                	and codProj = ?
                ) AS existe;""";

        Boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, codUsuario, idProjeto);

        return existe != null && existe;
    }

}
