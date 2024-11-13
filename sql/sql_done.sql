create database jobController;
use jobController;
-- drop database jobController;

create table usuario (
codUsuario int auto_increment primary key,
nome VARCHAR(40),
email VARCHAR(100) UNIQUE,
telefone CHAR(11) UNIQUE,
senha VARCHAR(60)
);

create table projeto (
codProjeto int auto_increment primary key,
titulo VARCHAR(50),
codPatroc int,
codCandidato int,
descricao text,
remuneracao decimal,
status_proj VARCHAR(20)
);

create table candidatura (
idCandidatura int auto_increment primary key,
codProj int,
codCandidato int
);

alter table projeto add constraint fk_projeto_patrocinador foreign key (codPatroc) references usuario(codUsuario) on delete CASCADE;
alter table projeto add constraint fk_projeto_candidato foreign key (codCandidato) references usuario(codUsuario) on delete CASCADE;
alter table candidatura add constraint fk_candidatura_projeto foreign key (codProj) references projeto(codProjeto) on delete CASCADE;
alter table candidatura add constraint fk_candidatura_usuario foreign key (codCandidato) references usuario(codUsuario) on delete CASCADE;
ALTER TABLE candidatura ADD CONSTRAINT unique_candidatura UNIQUE (codProj, codCandidato);

-- buscarPessoaPeloEmail(in email)
select codUsuario, nome, email, telefone, senha from usuario where email = "ruanmariano@gmail.com";

-- salvarPessoa(in Pessoa)
INSERT INTO usuario (nome, email, telefone, senha) VALUES ("Luan Mariano", "luanmariano@gmail.com", "11976924745", "senha123");

-- atualizarPessoa(in Pessoa)
update usuario set nome = "Ruan Mariano", email = "ruanmariano@gmail.com", telefone = "11976924789", senha = "senha123" where codUsuario = 1;


-- CONSULTA
-- verificarProjetoExiste() 
select exists (
	select True from projeto
	where codProjeto = 12
) AS existe;

-- atualizarProjeto(in Projeto)
update projeto set titulo = "Desenvolvimento Web", codPatroc = 1, descricao = "Projeto de criação de um site responsivo", remuneracao = 5000.00, status_proj = "Aberto" where codProjeto = 1;

-- verificarRelacaoPessoaProjeto()
select exists (
	select True from projeto
	where codProjeto = 1
    and codPatroc = 1
) AS existe;

-- deletarProjeto(in codProjeto)
-- delete from projeto where codProjeto = 2;

-- buscarProjetoId()
select p.codProjeto, p.titulo, p.descricao, p.remuneracao, u.email as emailPatrocinador, p.status_proj as statusProjeto 
from projeto p
inner join usuario u
where p.codPatroc = u.codUsuario
and codProjeto = 1;
    
-- RELATÓRIO
-- List<ResponsePessoaDto> listarPessoasCandidatada()
select u.nome, u.email, u.telefone
from candidatura c
inner join projeto p on c.codProj = p.codProjeto
inner join usuario u on c.codCandidato = u.codUsuario
where c.codProj = 1;

-- List<ResponseProjetoDto> listarProjetos();
select p.codProjeto, p.titulo, p.descricao, p.remuneracao, u.email as emailPatrocinador, p.status_proj as statusProjeto 
from projeto p
inner join usuario u
where p.codPatroc = u.codUsuario;

-- candidatar()
insert into candidatura (codProj, codCandidato) values (3, 5);

-- aceitarCandidato()
update projeto set codCandidato = 2, status_proj = "Fechado" where codProjeto = 1;

-- existeTelefone()
select exists (
	select True from usuario
	where telefone = "11976924789"
) AS existe;


DELIMITER $$
CREATE PROCEDURE cadastrarProjeto(
    IN titulo_in VARCHAR(50), 
    IN codPatroc_in INT, 
    IN descricao_in TEXT, 
    IN remuneracao_in DECIMAL, 
    IN status_proj_in VARCHAR(20),
    OUT codProjeto_out INT
)
BEGIN
    INSERT INTO projeto (titulo, codPatroc, descricao, remuneracao, status_proj) 
    VALUES (titulo_in, codPatroc_in, descricao_in, remuneracao_in, status_proj_in);
    
    SET codProjeto_out = LAST_INSERT_ID();
END $$
DELIMITER ;

-- drop procedure cadastrarProjeto;
