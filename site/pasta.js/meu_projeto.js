document.addEventListener("DOMContentLoaded", () => {
    // Função para cadastrar projeto
    const cadastrarProjeto = async (e) => {
        e.preventDefault();

        const form = e.target;
        const titulo = form.titulo.value;
        const descricao = form.descricao.value;
        const remuneracao = form.remuneracao.value;

        const novoProjeto = {
            titulo: titulo,
            descricao: descricao,
            remuneracao: remuneracao
        };

        try {
            const response = await fetch('http://localhost:8080/cadastrar-projeto', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(novoProjeto),
            });

            if (response.ok) {
                alert('Projeto cadastrado com sucesso!');
                form.reset(); // Limpar o formulário
            } else {
                alert('Erro ao cadastrar o projeto.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao cadastrar o projeto.');
        }
    };

    // Função para buscar projeto
    const buscarProjeto = async (e) => {
        e.preventDefault();

        const form = e.target;
        const idProjeto = form.id_projeto.value;

        try {
            const response = await fetch(`http://localhost:8080/buscar-projeto/${idProjeto}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (response.ok) {
                const projeto = await response.json();
                alert('Projeto encontrado: ' + JSON.stringify(projeto, null, 2));
            } else {
                alert('Projeto não encontrado.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao buscar o projeto.');
        }
    };

    // Função para atualizar projeto
    const atualizarProjeto = async (e) => {
        e.preventDefault();

        const idProjeto = document.getElementById('id_projeto').value;
        const titulo = document.getElementById('titulo').value;
        const descricao = document.getElementById('descricao').value;
        const remuneracao = document.getElementById('remuneracao').value;

        const projetoAtualizado = {
            id: idProjeto,
            titulo: titulo,
            descricao: descricao,
            remuneracao: remuneracao
        };

        try {
            const response = await fetch(`http://localhost:8080/atualizar-projeto/${idProjeto}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(projetoAtualizado),
            });

            if (response.ok) {
                alert('Projeto atualizado com sucesso!');
            } else {
                alert('Erro ao atualizar o projeto.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao atualizar o projeto.');
        }
    };

    // Função para deletar projeto
    const deletarProjeto = async (e) => {
        e.preventDefault();

        const idProjeto = document.getElementById('id_projeto').value;

        try {
            const response = await fetch(`http://localhost:8080/deletar-projeto/${idProjeto}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (response.ok) {
                alert('Projeto deletado com sucesso!');
            } else {
                alert('Erro ao deletar o projeto.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Ocorreu um erro ao deletar o projeto.');
        }
    };

// Função para Listar Candidatos projeto
        const listarCandidato = async (e) => {
            e.preventDefault();
    
            const idProjeto = document.getElementById('id_projeto').value;
    
            try {
                const response = await fetch(`http://localhost:8080/listar-candidatos/${idProjeto}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                });
    
                if (!response.ok) {
                    throw new Error(`Erro ao buscar projeto: ${response.status}`);
                }
        
                const data = await response.json();


                // Exibir os detalhes do projeto

                const projectDetails = document.querySelector(".candidates-list");
                data.dado.forEach((projeto) => {
                    console.log(projeto)
                    projectDetails.innerHTML = `
                    <p>Nome:${projeto.nome}</p>
                    <p>Email:${projeto.email}</p>
                    <p>Telefone:${projeto.telefone}</p>
                    <button onclick='aceitarCandidato("${projeto.email}",${idProjeto})'>Aceitar Candidato</button>`;
                });

            } catch (error) {
                console.error('Erro na requisição:', error);
                alert('Ocorreu um erro ao listar candidatos.');
            }
        };


    // Adicionar event listeners aos formulários e botões
    document.getElementById('formProjeto').addEventListener('submit', cadastrarProjeto);
    document.getElementById('buscarProjetoForm').addEventListener('submit', buscarProjeto);
    document.getElementById('atualizarProjetoButton').addEventListener('click', atualizarProjeto);
    document.getElementById('deletarProjetoButton').addEventListener('click', deletarProjeto);
    document.getElementById('listarCandidatoButton').addEventListener('click', listarCandidato);
});

        // Função para Aceitar um candidato
        async function aceitarCandidato(email,idProjeto) {
            const body={
                emailPessoa:email,
                idProjeto:idProjeto,
            }
            try {
                const response = await fetch(`http://localhost:8080/aceitar-canditado`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body:JSON.stringify(body)
                });
        
                if (!response.ok) {
                    throw new Error(`Erro ao aceitar Candidato: ${response.status}`);
                }
        
                const data = await response.json();
                console.log(data);
                alert(data.mensagem);
            } catch (error) {
                console.error(error);
                alert("Erro ao realizar a candidatura.");
            }
        }
