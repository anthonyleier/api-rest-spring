package br.com.anthonycruz.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.controllers.PersonController;
import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.data.dto.v2.PersonDTOv2;
import br.com.anthonycruz.exceptions.RequiredObjectIsNullException;
import br.com.anthonycruz.exceptions.ResourceNotFoundException;
import br.com.anthonycruz.mapper.DTOMapper;
import br.com.anthonycruz.mapper.custom.PersonMapper;
import br.com.anthonycruz.models.Person;
import br.com.anthonycruz.repositories.PersonRepository;

@Service
public class PersonService {
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonMapper personMapper;

	public List<PersonDTO> findAll() {
		logger.info("Searching for all people");
		List<PersonDTO> personDTOs = DTOMapper.parseListObjects(repository.findAll(), PersonDTO.class);
		personDTOs.stream().forEach(personDTO -> personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel()));
		return personDTOs;
	}

	public PersonDTO findById(Long id) {
		logger.info("Searching for person with ID " + id);
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var personDTO = DTOMapper.parseObject(entity, PersonDTO.class);
		personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personDTO;
	}

	public PersonDTO create(PersonDTO personDTO) {
		if (personDTO == null) throw new RequiredObjectIsNullException();
		logger.info("Creating new person");
		
		var entity = DTOMapper.parseObject(personDTO, Person.class);
		var entitySaved = repository.save(entity);
		var personDTOResponse = DTOMapper.parseObject(entitySaved, PersonDTO.class);
		
		personDTOResponse.add(linkTo(methodOn(PersonController.class).findById(personDTOResponse.getKey())).withSelfRel());
		return personDTOResponse;
	}

	public PersonDTOv2 createV2(PersonDTOv2 personDTOv2) {
		logger.info("Creating personDTOv2");
		var entity = personMapper.convertDTOToEntity(personDTOv2);
		var entitySaved = repository.save(entity);
		return personMapper.convertEntityToDTO(entitySaved);
	}

	public PersonDTO update(PersonDTO personDTO) {
		if (personDTO == null) throw new RequiredObjectIsNullException();
		logger.info("Updating person with ID " + personDTO.getKey());
		
		var person = repository.findById(personDTO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());
		person.setAddress(personDTO.getAddress());
		person.setGender(personDTO.getGender());

		var savedPerson = repository.save(person);
		var savedPersonDTO = DTOMapper.parseObject(savedPerson, PersonDTO.class);
		savedPersonDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
		return savedPersonDTO;
	}

	public void delete(Long id) {
		logger.info("Deleting person with ID " + id);
		var person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(person);
	}
}
