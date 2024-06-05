package br.com.anthonycruz.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfig {
	private Logger logger = Logger.getLogger(OpenAIConfig.class.getName());

	@Value("${openai.api.key}")
	String apiKey;

	@Bean
	RestTemplate template() {
		logger.info("Initializing RestTemplate");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add("Authorization", "Bearer " + apiKey);
			return execution.execute(request, body);
		});
		return restTemplate;
	}
}
