package br.com.anthonycruz.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenAIConfig {
	private Logger logger = Logger.getLogger(OpenAIConfig.class.getName());

	@Value("${openai.api.key}")
	String apiKey;

	@Bean
	WebClient webClient(WebClient.Builder webClientBuilder) {
		logger.info("Initializing WebClient");
		return webClientBuilder.defaultHeader("Authorization", "Bearer " + apiKey).build();
	}
}
