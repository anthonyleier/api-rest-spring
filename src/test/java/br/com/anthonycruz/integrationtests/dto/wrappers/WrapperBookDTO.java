package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperBookDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private BookEmbeddedDTO embedded;

	public WrapperBookDTO() {}

	public BookEmbeddedDTO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(BookEmbeddedDTO embedded) {
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
		WrapperBookDTO other = (WrapperBookDTO) obj;
		return Objects.equals(embedded, other.embedded);
	}
}
