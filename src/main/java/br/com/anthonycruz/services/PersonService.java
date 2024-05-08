package br.com.anthonycruz.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.anthonycruz.models.Person;

@Service
public class PersonService {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public Person findById(String id) {
		logger.info("Finding one person");
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Anthony");
		person.setLastName("Cruz");
		person.setAddress("Caçador/SC");
		person.setGender("Male");
		return person;
	}
	
	public List<Person> findAll() {
		List<Person> persons = new ArrayList<>();
		for (int i=0; i< 8;i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons ;
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("First name " + i);
		person.setLastName("Last name " + i);
		person.setAddress("Address " + i);
		person.setGender("Male");
		return person;
	}
}
