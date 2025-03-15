package thelazycoder.blog_app.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog API Documentation")
                        .description("API documentation for the Blog application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("The Lazy Coder")
                                .email("lazycoder@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://thelazycoder.com/blog-api-docs"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server")
                ));
    }

    @Bean
    public OpenApiCustomizer customizeSwagger() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
            if (path.startsWith("/api/signup")) {  // Exempt public endpoints from security
                pathItem.readOperations().forEach(operation -> {
                    operation.setSecurity(null);
                });
            }
        });
    }

}
