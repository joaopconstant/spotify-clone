package br.com.ibmec.cloud.spotifyclone;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("IBMEC - CLOUD")
                        .description("Exemplo de Open Api")
                        .version("1.0")
                        .contact(new Contact()
                                .email("joaoconstantdev@gmail.com")
                                .name("João Pedro Constant")
                        )
                );
    }
}
