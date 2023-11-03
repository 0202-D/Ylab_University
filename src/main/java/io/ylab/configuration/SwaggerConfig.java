package io.ylab.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.info.title}")
    private String title;

    @Value("${swagger.info.description}")
    private String description;
    @Bean
    public GroupedOpenApi myGroupedOpenApi() {
        String[] packagesToScan = {"io.ylab"};
        return GroupedOpenApi.builder()
                .group("my-api")
                .packagesToScan(packagesToScan)
                .build();
    }
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(apiInfo());
    }
    private Info apiInfo() {
        return new Info()
                .title(title)
                .description(description);
    }
}
