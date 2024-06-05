package br.com.anthonycruz.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {
	private Logger logger = Logger.getLogger(OpenAIConfig.class.getName());

	@Value("${openai.api.key}")
	String apiKey;
}
