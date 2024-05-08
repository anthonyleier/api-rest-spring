package br.com.anthonycruz.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.exceptions.ResourceNotFoundException;
import br.com.anthonycruz.models.Person;
import br.com.anthonycruz.repositories.PersonRepository;

@Service
public class PersonService {
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	PersonRepository repository;

	public Person findById(Long id) {
		logger.info("Finding one person");
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

	public Person create(Person person) {
		logger.info("Creating person");
		return repository.save(person);
	}

	public Person update(Person person) {
		logger.info("Updating person");
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return repository.save(entity);
	}

	public void delete(Long id) {
		logger.info("Deleting person");
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
}
