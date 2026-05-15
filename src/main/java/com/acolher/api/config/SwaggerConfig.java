package com.acolher.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
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
		                .apis(RequestHandlerSelectors.basePackage("com.acolher.api"))
		                .paths(regex("/api.*"))
		                .build()
		                .apiInfo(metaInfo());
		    }
		   
			private ApiInfo metaInfo() {

		        @SuppressWarnings("rawtypes")
				ApiInfo apiInfo = new ApiInfo(
		                "Acolher",
		                "Backend do projeto acolher.",
		                "1.0",
		                "Terms of Service",
		                new Contact("Acolher", "https://github.com/daienelima/acolher",
		                        ""),
		                "Apache License Version 2.0",
		                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
		        );

		        return apiInfo;
		    }
}
