package com.example.apiGateway.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Study Wiz API Documentation")
                        .version("1.0")
                        .description("API documentation for Study Wiz"));
    }
//    @Bean
//    public GroupedOpenApi userApi() {
//        return GroupedOpenApi.builder()
//                .group("user-service")
//                .pathsToMatch("/user-service/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi roomApi() {
//        return GroupedOpenApi.builder()
//                .group("room-service")
//                .pathsToMatch("/room-service/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi videoApi() {
//        return GroupedOpenApi.builder()
//                .group("video-service")
//                .pathsToMatch("/video-service/**")
//                .build();
//    }
}
