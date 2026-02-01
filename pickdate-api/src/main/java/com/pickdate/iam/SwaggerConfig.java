package com.pickdate.iam;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
class SwaggerConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI swaggerDocumentation() {
        String basePath = (contextPath == null || contextPath.isBlank()) ? "/" : contextPath;
        String localUrl = "http://localhost:8080" + ((contextPath == null || contextPath.isBlank()) ? "" : contextPath);

        var components = new Components()
                .addSecuritySchemes("basicAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic"));

        var globalSecurity = new SecurityRequirement()
                .addList("basicAuth");

        return new OpenAPI()
                .components(components)
                .addSecurityItem(globalSecurity)
                .info(
                        new Info().title("Pickdate API")
                                .description("Public REST API for managing polls, votes, user accounts, activity events and problem/error reports.")
                                .version("v1")
                                .license(new License().name("AGPL-3.0").url("https://www.gnu.org/licenses/agpl-3.0.en.html"))
                )
                .servers(List.of(
                        new Server().url(basePath).description("Default server (respects context path)"),
                        new Server().url(localUrl).description("Local development server"))
                );
    }

    @Bean
    GroupedOpenApi userApiGroup() {
        return GroupedOpenApi.builder()
                .group("public api")
                .pathsToExclude("/api/v1/iam/**")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    GroupedOpenApi adminApiGroup() {
        return GroupedOpenApi.builder()
                .group("admin api")
                .pathsToMatch("/api/v1/iam/**")
                .build();
    }
}
