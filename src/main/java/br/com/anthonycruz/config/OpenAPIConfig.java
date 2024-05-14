package br.com.anthonycruz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(
						new Info()
						.title("RESTful API com Java/Spring Boot")
						.version("v1")
						.description("Aprendizado de Spring Boot com API REST e padrões de projeto")
						.termsOfService("https://developers.google.com/terms")
						.license(
								new License()
								.name("Apache 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0"))
						);
	}
}
