package br.com.anthonycruz.webclient.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatGPTRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String model;
	private List<Message> messages;

	public ChatGPTRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		return Objects.hash(messages, model);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ChatGPTRequest other = (ChatGPTRequest) obj;
		return Objects.equals(messages, other.messages) && Objects.equals(model, other.model);
	}
}
