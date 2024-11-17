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
            throw new Error(`Erro ao listar projetos: ${response.status}`);
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
        console.error(error);
        alert("Erro ao carregar a lista de projetos.");
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
            throw new Error(`Erro ao se candidatar: ${response.status}`);
        }

        const data = await response.json();
        console.log(data);
        alert(data.mensagem);
    } catch (error) {
        console.error(error);
        alert("Erro ao realizar a candidatura.");
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
            throw new Error(`Erro ao buscar projeto: ${response.status}`);
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
        console.error(error);
        alert("Erro ao carregar os detalhes do projeto.");
    }
}


// Chamada inicial para listar os projetos ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
    listarProjetos();
});
