package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.anthonycruz.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonEmbeddedDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("personDTOList")
	private List<PersonDTO> people;

	public PersonEmbeddedDTO() {}

	public List<PersonDTO> getPeople() {
		return people;
	}

	public void setPeople(List<PersonDTO> people) {
		this.people = people;
	}

	@Override
	public int hashCode() {
		return Objects.hash(people);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PersonEmbeddedDTO other = (PersonEmbeddedDTO) obj;
		return Objects.equals(people, other.people);
	}
}
