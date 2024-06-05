package br.com.anthonycruz.webclient.services;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.anthonycruz.webclient.request.ChatGPTRequest;
import br.com.anthonycruz.webclient.response.ChatGPTResponse;
import reactor.core.publisher.Mono;

@Service
public class ChatGPTService {
	private Logger logger = Logger.getLogger(ChatGPTService.class.getName());

	@Value("${openai.model}")
	private String model;

	@Value("${openai.api.url}")
	private String url;

	@Autowired
	private WebClient webClient;

	public Object chat(String prompt) {
		logger.info("Starting chat with prompt");
		ChatGPTRequest request = new ChatGPTRequest(model, prompt);
		logger.info("Sending prompt");
		Mono<ChatGPTResponse> responseMono = webClient.post().uri(url).bodyValue(request).retrieve().bodyToMono(ChatGPTResponse.class);
		ChatGPTResponse response = responseMono.block();
		return response.getChoices().get(0).getMessage().getContent();
	}
}
