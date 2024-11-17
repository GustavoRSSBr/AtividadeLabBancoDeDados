const login = async (e) => {
    e.preventDefault(); // Evita o envio padrão do formulário

    // Obtém o formulário e os dados inseridos
    const form = e.target;
    const formData = new FormData(form);
    const jsonData = JSON.stringify(Object.fromEntries(formData.entries())); // Converte para JSON

    try {
        // Envia os dados para o backend
        const response = await fetch('http://localhost:8080/login', {
            headers: { 'Content-Type': 'application/json' },
            method: 'POST',
            body: jsonData,
        });

        // Verifica o status da resposta
        if (!response.ok) {
            // Lê a mensagem de erro retornada pelo backend
            const errorResponse = await response.json();
            throw new Error(errorResponse.mensagem || 'Erro ao realizar o login');
        }

        // Processa a resposta do backend para um login bem-sucedido
        const jsonResponse = await response.json();
        alert(jsonResponse.mensagem); // Exibe mensagem de sucesso
        window.location.href = "meu_projeto.html"; // Redireciona para a próxima página
    } catch (error) {
        console.error('Erro ao realizar o login:', error);
        alert(error.message); // Exibe a mensagem de erro para o usuário
    }
};
