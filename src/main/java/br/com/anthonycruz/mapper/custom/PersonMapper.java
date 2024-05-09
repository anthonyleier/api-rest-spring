package br.com.anthonycruz.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.anthonycruz.data.dto.v2.PersonDTOv2;
import br.com.anthonycruz.models.Person;

@Service
public class PersonMapper {
	public PersonDTOv2 convertEntityToDTO(Person person) {
		PersonDTOv2 personDTOv2 = new PersonDTOv2();
		personDTOv2.setId(person.getId());
		personDTOv2.setFirstName(person.getFirstName());
		personDTOv2.setLastName(person.getLastName());
		personDTOv2.setAddress(person.getAddress());
		personDTOv2.setGender(person.getGender());
		personDTOv2.setBirthDay(new Date());
		return personDTOv2;
	}
	
	public Person convertDTOToEntity(PersonDTOv2 personDTOv2) {
		Person person = new Person();
		person.setId(personDTOv2.getId());
		person.setFirstName(personDTOv2.getFirstName());
		person.setLastName(personDTOv2.getLastName());
		person.setAddress(personDTOv2.getAddress());
		person.setGender(personDTOv2.getGender());
		return person;
	}
}
