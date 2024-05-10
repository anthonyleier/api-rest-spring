package br.com.anthonycruz.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.mapper.mocks.MockPerson;
import br.com.anthonycruz.models.Person;
import br.com.anthonycruz.repositories.PersonRepository;
import br.com.anthonycruz.services.PersonService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	MockPerson input;

	@InjectMocks
	private PersonService service;

	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		var testID = 1L;
		Person entity = input.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		var result = service.findById(testID);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/person/1"));
		assertEquals("Address Test 1", result.getAddress());
		assertEquals("First Name Test 1", result.getFirstName());
		assertEquals("Last Name Test 1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		var testID = 2L;
		
		Person persisted = input.mockEntity(testID);
		PersonDTO personDTO = input.mockDTO(testID);
		personDTO.setKey(testID);
	
		when(repository.save(any(Person.class))).thenReturn(persisted);
		var result = service.create(personDTO);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/person/2"));
		assertEquals("Address Test 2", result.getAddress());
		assertEquals("First Name Test 2", result.getFirstName());
		assertEquals("Last Name Test 2", result.getLastName());
		assertEquals("Male", result.getGender());
	}

	@Test
	void testUpdate() {
		var testID = 3L;
		
		Person entity = input.mockEntity(testID);
		Person persisted = entity;
		PersonDTO personDTO = input.mockDTO(testID);
		
		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(personDTO);
		System.out.println(result.getFirstName());

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/person/3"));
		assertEquals("Address Test 3", result.getAddress());
		assertEquals("First Name Test 3", result.getFirstName());
		assertEquals("Last Name Test 3", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testDelete() {
		var testID = 1L;
		Person entity = input.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		service.delete(testID);
	}

}
