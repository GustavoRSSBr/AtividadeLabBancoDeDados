package com.bd.projob.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SimpleCorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Configurações de CORS
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500"); // Substitua pela origem do seu frontend
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Métodos permitidos
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // Cabeçalhos permitidos
        response.setHeader("Access-Control-Allow-Credentials", "true"); // Permite envio de credenciais
        response.setHeader("Access-Control-Max-Age", "3600"); // Cache para preflight requests

        // Tratamento de preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continua com o restante da cadeia de filtros
        filterChain.doFilter(request, response);
}
}