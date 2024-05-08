package br.com.anthonycruz.services;

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
}
