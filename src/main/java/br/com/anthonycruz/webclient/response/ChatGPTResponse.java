package br.com.anthonycruz.webclient.response;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ChatGPTResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Choice> choices;

	public ChatGPTResponse() {}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	@Override
	public int hashCode() {
		return Objects.hash(choices);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ChatGPTResponse other = (ChatGPTResponse) obj;
		return Objects.equals(choices, other.choices);
	}
}
