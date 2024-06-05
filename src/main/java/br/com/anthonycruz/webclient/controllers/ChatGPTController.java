package br.com.anthonycruz.webclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.anthonycruz.webclient.services.ChatGPTService;

@RestController
@RequestMapping("/gpt")
public class ChatGPTController {
	@Autowired
	private ChatGPTService service;

	@GetMapping("/chat")
	public Object chat(@RequestParam("prompt") String prompt) {
		return service.chat(prompt);
	}
}
