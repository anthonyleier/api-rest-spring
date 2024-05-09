package br.com.anthonycruz.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.data.dto.v2.PersonDTOv2;
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

	public PersonDTO findById(Long id) {
		logger.info("Finding one personDTO");
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DTOMapper.parseObject(entity, PersonDTO.class);
	}

	public List<PersonDTO> findAll() {
		return DTOMapper.parseListObjects(repository.findAll(), PersonDTO.class);
	}

	public PersonDTO create(PersonDTO personDTO) {
		logger.info("Creating personDTO");
		Person entity = DTOMapper.parseObject(personDTO, Person.class);
		Person entitySaved = repository.save(entity);
		return DTOMapper.parseObject(entitySaved, PersonDTO.class);
	}
	
	public PersonDTOv2 createV2(PersonDTOv2 personDTOv2) {
		logger.info("Creating personDTOv2");
		Person entity = personMapper.convertDTOToEntity(personDTOv2);
		Person entitySaved = repository.save(entity);
		return personMapper.convertEntityToDTO(entitySaved);
	}

	public PersonDTO update(PersonDTO personDTO) {
		logger.info("Updating personDTO");
		Person entity = repository.findById(personDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(personDTO.getFirstName());
		entity.setLastName(personDTO.getLastName());
		entity.setAddress(personDTO.getAddress());
		entity.setGender(personDTO.getGender());
		
		Person entitySaved = repository.save(entity);
		return DTOMapper.parseObject(entitySaved, PersonDTO.class);
	}

	public void delete(Long id) {
		logger.info("Deleting personDTO");
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
}
