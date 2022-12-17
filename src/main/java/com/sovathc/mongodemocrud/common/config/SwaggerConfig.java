package com.sovathc.mongodemocrud.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder().group("Api").pathsToMatch("/api/**").build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()

                .info(
                        new Info()
                                .title("Mongodb simple crud Rest Api")
                                .description("Rest Api for web application")
                                .version("1.0"));
    }
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE));
    }
}
