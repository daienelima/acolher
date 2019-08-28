package com.acolher.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
			//http://localhost:8080/swagger-ui.html
			@Bean
		    public Docket productApi() {
		        return new Docket(DocumentationType.SWAGGER_2)
		                .select()
		                .apis(RequestHandlerSelectors.any())
		                .paths(PathSelectors.ant("/api/**"))
		                .build()
		                .apiInfo(this.informacoesApi().build());
		    }
		   
			public ApiInfoBuilder informacoesApi() {
				ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
				 
				apiInfoBuilder.title("Api-Acolher");
				apiInfoBuilder.description("Backend projeto acolher");
				apiInfoBuilder.version("1.0");
				apiInfoBuilder.termsOfServiceUrl("");
				apiInfoBuilder.contact(this.contato());
				
				return apiInfoBuilder;
			}
			
			private Contact contato() {
				return new Contact(
						"https://github.com/daienelima/acolher", null, null);
			}
}
