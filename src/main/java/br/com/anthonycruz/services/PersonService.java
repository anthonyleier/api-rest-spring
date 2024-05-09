package br.com.anthonycruz.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.exceptions.ResourceNotFoundException;
import br.com.anthonycruz.repositories.PersonRepository;

@Service
public class PersonService {
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	PersonRepository repository;

	public PersonDTO findById(Long id) {
		logger.info("Finding one personDTO");
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public List<PersonDTO> findAll() {
		return repository.findAll();
	}

	public PersonDTO create(PersonDTO personDTO) {
		logger.info("Creating personDTO");
		return repository.save(personDTO);
	}

	public PersonDTO update(PersonDTO personDTO) {
		logger.info("Updating personDTO");
		PersonDTO entity = repository.findById(personDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(personDTO.getFirstName());
		entity.setLastName(personDTO.getLastName());
		entity.setAddress(personDTO.getAddress());
		entity.setGender(personDTO.getGender());

		return repository.save(entity);
	}

	public void delete(Long id) {
		logger.info("Deleting personDTO");
		PersonDTO entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
}
