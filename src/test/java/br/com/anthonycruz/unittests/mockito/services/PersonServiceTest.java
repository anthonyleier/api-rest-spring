package br.com.anthonycruz.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import br.com.anthonycruz.exceptions.RequiredObjectIsNullException;
import br.com.anthonycruz.mapper.mocks.MockPerson;
import br.com.anthonycruz.models.Person;
import br.com.anthonycruz.repositories.PersonRepository;
import br.com.anthonycruz.services.PersonService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	@InjectMocks
	private PersonService service;

	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		var testID = 1L;
		Person entity = MockPerson.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		var result = service.findById(testID);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/persons/1"));
		assertEquals("Address Test 1", result.getAddress());
		assertEquals("First Name Test 1", result.getFirstName());
		assertEquals("Last Name Test 1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreate() {
		var testID = 2L;

		Person persisted = MockPerson.mockEntity(testID);
		PersonDTO personDTO = MockPerson.mockDTO(testID);

		when(repository.save(any(Person.class))).thenReturn(persisted);
		var result = service.create(personDTO);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/persons/2"));
		assertEquals("Address Test 2", result.getAddress());
		assertEquals("First Name Test 2", result.getFirstName());
		assertEquals("Last Name Test 2", result.getLastName());
		assertEquals("Male", result.getGender());
	}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "Its not allowed to persist a null object";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		var testID = 3L;

		Person entity = MockPerson.mockEntity(testID);
		Person persisted = entity;
		PersonDTO personDTO = MockPerson.mockDTO(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(personDTO);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/persons/3"));
		assertEquals("Address Test 3", result.getAddress());
		assertEquals("First Name Test 3", result.getFirstName());
		assertEquals("Last Name Test 3", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});

		String expectedMessage = "Its not allowed to persist a null object";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		var testID = 1L;
		Person entity = MockPerson.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(entity));
		service.delete(testID);
	}

}
