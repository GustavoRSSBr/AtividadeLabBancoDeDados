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
                const data = await response.json();
    
                // Atualiza o campo de ID com o dado retornado pelo backend
                document.getElementById('id_projeto').value = data.dado;
    
                alert(data.mensagem || 'Projeto cadastrado com sucesso!');
                form.reset(); // Limpa o formulário
            } else {
                const errorResponse = await response.json();
                throw new Error(errorResponse.mensagem || 'Erro ao cadastrar projeto');
            }
        } catch (error) {
            console.error('Erro ao cadastrar projeto', error);
            alert(error.message); // Exibe a mensagem de erro para o usuário
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
                const errorResponse = await response.json();
                throw new Error(errorResponse.mensagem || 'Erro ao buscar projeto');
            }
        } catch (error) {
            console.error('Erro ao buscar projeto:', error);
            alert(error.message); // Exibe a mensagem de erro para o usuário
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
                const errorResponse = await response.json();
                throw new Error(errorResponse.mensagem || 'Erro ao atualizar projeto');
            }
        } catch (error) {
            console.error('Erro ao atualizar projeto:', error);
            alert(error.message); // Exibe a mensagem de erro para o usuário
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
                const errorResponse = await response.json();
                throw new Error(errorResponse.mensagem || 'Erro ao deletar projeto');
            }
        } catch (error) {
            console.error('Erro ao deletar:', error);
            alert(error.message); // Exibe a mensagem de erro para o usuário
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
            // Lê a mensagem de erro retornada pelo backend
            const errorResponse = await response.json();
            throw new Error(errorResponse.mensagem || 'Erro ao Listar Candidatos projeto:');
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
                    <button class="btn-aceitarCandidato" onclick='aceitarCandidato("${projeto.email}",${idProjeto})'>Aceitar Candidato</button>`;
                });

            } catch (error) {
                console.error('Erro ao Listar Candidatos projeto:', error);
                alert(error.message); // Exibe a mensagem de erro para o usuário
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
                    const errorResponse = await response.json();
                    throw new Error(errorResponse.mensagem || 'Erro ao aceitar candidato');
                }
        
                const data = await response.json();
                console.log(data);
                alert(data.mensagem);
            } catch (error) {
                console.error('Erro ao realizar candidatura:', error);
                alert(error.message);
            }
        }
