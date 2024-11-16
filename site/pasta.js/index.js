const login = async (e) => {
    e.preventDefault(); // Evita o envio padrão do formulário
    // Obtém o formulário a partir do evento
    const form = e.target;
    // Cria um objeto FormData para coletar os dados do formulário
    const formData = new FormData(form);
    // Converte os dados do formulário em um objeto JSON
    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));
    try {
        const response = await fetch('http://localhost:8080/login', {
            headers: { 'Content-Type': 'application/json' },
            method: 'POST',
            body: jsonData,
        });
        e.target.reset();
        console.log(response.json);
        
    } catch (error) {
        console.log(error);
    }

};
