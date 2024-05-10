package br.com.anthonycruz.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.models.Person;

public class MockPerson {
	public Person mockEntity() {
		return mockEntity(0);
	}

	public PersonDTO mockDTO() {
		return mockDTO(0);
	}

	public List<Person> mockEntityList() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}

	public List<PersonDTO> mockDTOList() {
		List<PersonDTO> persons = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockDTO(i));
		}
		return persons;
	}

	public Person mockEntity(Integer number) {
		Person person = new Person();
		person.setId(number.longValue());
		person.setFirstName("First Name Test" + number);
		person.setLastName("Last Name Test" + number);
		person.setAddress("Address Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		return person;
	}

	public PersonDTO mockDTO(Integer number) {
		PersonDTO person = new PersonDTO();
		person.setKey(number.longValue());
		person.setFirstName("First Name Test" + number);
		person.setLastName("Last Name Test" + number);
		person.setAddress("Address Test" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		return person;
	}
}
