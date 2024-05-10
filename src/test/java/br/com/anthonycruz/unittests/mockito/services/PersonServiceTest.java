package br.com.anthonycruz.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
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

	// deletar duas linhas de entity e passar input.mockEntity(1) no de baixo
	// when(repository.save(any(Person.class))).thenReturn(persisted);

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		var testID = 1L;
		Person person = input.mockEntity(testID);
		
		when(repository.findById(testID)).thenReturn(Optional.of(person));
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
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
