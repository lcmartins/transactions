package com.lucianomartins.transactions.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfiguration {

    @Bean
    fun configureSwaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                ApiInfoBuilder().title("financial api")
                    .description("api to create customers, accounts and make transfers")
                    .version("1.0").build()
            )
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.lucianomartins.transactions.controllers.v1"))
            .build()
    }
}