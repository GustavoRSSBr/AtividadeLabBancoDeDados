const cadastro = async (e) => {
    e.preventDefault(); // Evita o envio padrão do formulário

    const form = e.target;
    const formData = new FormData(form);
    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

    try {
        const response = await fetch('http://localhost:8080/cadastrar-pessoa', {
            headers: { 'Content-Type': 'application/json' },
            method: 'POST',
            body: jsonData,
        });

        if (response.ok) {
            const data = await response.json();
            alert(` ${data.mensagem}`);
            form.reset();
        } else {
            const errorData = await response.json(); // Processa o JSON de erro
            alert(`${errorData.mensagem || 'Erro desconhecido'}`);
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro ao processar o cadastro.');
    }
};