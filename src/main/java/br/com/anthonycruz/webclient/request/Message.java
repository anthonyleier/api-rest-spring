package br.com.anthonycruz.webclient.request;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private String role;
	private String content;

	public Message(String role, String content) {
		this.role = role;
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Message other = (Message) obj;
		return Objects.equals(content, other.content) && Objects.equals(role, other.role);
	}
}
