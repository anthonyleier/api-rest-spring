package br.com.anthonycruz.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.models.Person;

public class MockPerson {
	public Person mockEntity() {
		return mockEntity(0L);
	}

	public PersonDTO mockDTO() {
		return mockDTO(0L);
	}

	public List<Person> mockEntityList() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(Long.valueOf(i)));
		}
		return persons;
	}

	public List<PersonDTO> mockDTOList() {
		List<PersonDTO> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockDTO(Long.valueOf(i)));
		}
		return persons;
	}

	public Person mockEntity(Long id) {
		Person person = new Person();
		person.setId(id);
		person.setFirstName("First Name Test " + id);
		person.setLastName("Last Name Test " + id);
		person.setAddress("Address Test " + id);
		person.setGender(((id % 2) == 0) ? "Male" : "Female");
		return person;
	}

	public PersonDTO mockDTO(Long id) {
		PersonDTO person = new PersonDTO();
		person.setKey(id);
		person.setFirstName("First Name Test " + id);
		person.setLastName("Last Name Test " + id);
		person.setAddress("Address Test " + id);
		person.setGender(((id % 2) == 0) ? "Male" : "Female");
		return person;
	}
}
