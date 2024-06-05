package br.com.anthonycruz.webclient.response;

import java.io.Serializable;
import java.util.Objects;

import br.com.anthonycruz.webclient.request.Message;

public class Choice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int index;
	private Message message;

	public Choice() {}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(index, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Choice other = (Choice) obj;
		return index == other.index && Objects.equals(message, other.message);
	}
}
