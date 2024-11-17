package com.bd.projob.config.cors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica CORS a todos os endpoints
                        .allowedOrigins("http://localhost:5173") // Permite acesso do frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite esses métodos HTTP
                        .allowedHeaders("*") // Permite todos os cabeçalhos
                        .exposedHeaders("Authorization") // Expor cabeçalho de autorização, se necessário
                        .allowCredentials(true) // Permitir envio de credenciais, como cookies ou cabeçalhos de autorização
                        .maxAge(3600); // Tempo máximo que a resposta do preflight request pode ser armazenada em cache
            }
        };
    }
}