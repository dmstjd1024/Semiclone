package com.semiclone.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*  
*   FileName : SwaggerConfig.java
*   1. Swagger 적용 범위 설정
*   2. Swagger UI 옵션 설정
*/ 
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /* Swagger API Setting */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo()) // Swagger API Info 사용
                        .select() // 
                        .apis(RequestHandlerSelectors.basePackage("com.semiclone.springboot")) // com.semiclone.springboot 패키지에 적용 
                        .paths(PathSelectors.any()) // 모든 Path(url)에 적용
                        .build();
    }

    /* Swagger API Info */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SemiClone") // Swagger 제목
                .description("swagger2 사용해 봅시다.") // Swagger 설명
                .build();
 
    }

}//end of SwaggerConfig