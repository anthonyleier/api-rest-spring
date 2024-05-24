package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperPersonDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private PersonEmbeddedDTO embedded;

	public WrapperPersonDTO() {}

	public PersonEmbeddedDTO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(PersonEmbeddedDTO embedded) {
		this.embedded = embedded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(embedded);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		WrapperPersonDTO other = (WrapperPersonDTO) obj;
		return Objects.equals(embedded, other.embedded);
	}
}
