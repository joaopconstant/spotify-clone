package br.com.ibmec.cloud.spotifyclone;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Mapeia para todas as rotas
                .allowedOrigins("https://salmon-smoke-0576ad10f.5.azurestaticapps.net")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);  // Permite credenciais (por exemplo, cookies)
    }
}
