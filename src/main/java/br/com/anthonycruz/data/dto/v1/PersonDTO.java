package br.com.anthonycruz.data.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "address" })
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;
	private String address;
	private String firstName;
	private String lastName;
	private String gender;
	private Boolean enabled;
	
	public PersonDTO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, enabled, firstName, gender, key, lastName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		PersonDTO other = (PersonDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(enabled, other.enabled) && Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender) && Objects.equals(key, other.key) && Objects.equals(lastName, other.lastName);
	}
}
