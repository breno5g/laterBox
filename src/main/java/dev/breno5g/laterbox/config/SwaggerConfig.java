package dev.breno5g.laterbox.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenAPI() {
        Info info = new Info();
        info.title("LaterBox API");
        info.description("LaterBox API");
        info.version("1.0.0");
        info.contact(new io.swagger.v3.oas.models.info.Contact().email("brenosantos@breno5g.dev"));

        Components components = new Components()
                .addSecuritySchemes(
                        "bearer-key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(
                "bearer-key"
        );

        return new OpenAPI().info(info).components(components).addSecurityItem(securityRequirement);
    }
}
