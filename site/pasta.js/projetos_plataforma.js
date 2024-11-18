// Função para listar todos os projetos
async function listarProjetos() {
    try {
        const response = await fetch("http://localhost:8080/listar-projetos", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            // Lê a mensagem de erro retornada pelo backend
            const errorResponse = await response.json();
            throw new Error(errorResponse.mensagem || 'Erro ao listar todos os projetos');
        }

        const data = await response.json();
        console.log(data);

        // Exibir os projetos na página
        const projectsList = document.querySelector(".projects-list");
        projectsList.innerHTML = ""; // Limpa a lista antes de exibir

        data.dado.forEach((projeto) => {
            console.log(projeto)
            const projectItem = document.createElement("div");
            projectItem.className = "project-item";
            projectItem.innerHTML = `
                <h3>${projeto.titulo}</h3>
                <button onclick="buscarProjeto(${projeto.codProjeto})">Exibir Detalhe</button>
            `;
            projectsList.appendChild(projectItem);
        });
    } catch (error) {
        console.error('Erro ao listar todos os projetos:', error);
        alert(error.message); // Exibe a mensagem de erro para o usuário
    }
}

// Função para se candidatar a um projeto
async function candidatarProjeto(idProjeto) {
    try {
        const response = await fetch(`http://localhost:8080/candidatar/${idProjeto}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            throw new Error(errorResponse.mensagem || 'Erro ao se candidatar a um projeto');
        }

        const data = await response.json();
        console.log(data);
        alert(data.mensagem);
    } catch (error) {
        console.error('Erro ao se candidatar a um projeto:', error);
        alert(error.message); // Exibe a mensagem de erro para o usuário
    }
}


// Função para buscar os detalhes de um projeto específico
async function buscarProjeto(idProjeto) {
    try {
        const response = await fetch(`http://localhost:8080/buscar-projeto/${idProjeto}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            throw new Error(errorResponse.mensagem || 'Erro ao buscar os detalhes de um projeto específico:');
        }

        const data = await response.json();
        console.log(data);

        // Exibir os detalhes do projeto
        const projectDetails = document.querySelector(".project-details");
        projectDetails.innerHTML = `
            <h3>${data.dado.titulo}</h3>
            <p>${data.dado.descricao}</p>
            <p>Remuneração: R$ ${data.dado.remuneracao}</p>
            <p>emailPatrocinador: ${data.dado.emailPatrocinador}</p>
            <p>Status: <strong>${data.dado.statusProjeto} </strong></p>
            <button onclick="candidatarProjeto(${data.dado.codProjeto})">Se Candidatar</button>`;
    } catch (error) {
        console.error('Erro ao buscar os detalhes de um projeto específico:', error);
        alert(error.message); // Exibe a mensagem de erro para o usuário
    }
}


// Chamada inicial para listar os projetos ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
    listarProjetos();
});
