use jobController;
INSERT INTO usuario (nome, email, telefone, senha) VALUES 
("Juan Mariano", "juanmariano@gmail.com", "11976924779", "senha123"),
("Carlos Souza", "carlos.souza@email.com", "11987654321", "password123"),
("Mariana Lima", "mariana.lima@email.com", "21912345678", "senha456"),
("Fernanda Oliveira", "fernanda.oliveira@email.com", "31987654321", "senha789"),
("Roberto Costa", "roberto.costa@email.com", "21923456789", "senha321");

INSERT INTO projeto (titulo, codPatroc, codCandidato, descricao, remuneracao, status_proj) VALUES 
("Desenvolvimento eb", 1, null, "Projeto de criação de um site responsivo", 5000.00, "Aberto"),
("Aplicativo Mobile", 2, 1, "Desenvolvimento de um aplicativo para controle de finanças", 8000.00, "Fechado"),
("Sistema IoT", 3, null, "Sistema de monitoramento para agricultura", 12000.00, "Aberto"),
("Plataforma EAD", 4, 3, "Desenvolvimento de uma plataforma de ensino a distância", 10000.00, "Fechado"),
("Marketing Digital", 5, null, "Projeto de campanha de marketing digital", 6000.00, "Aberto");

-- candidatar(Candidatura build?)
INSERT INTO candidatura (codProj, codCandidato) VALUES 
(1, 2),
(1, 3),
(2, 1),
(3, 4),
(4, 5),
(5, 3);