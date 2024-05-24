package br.com.anthonycruz.integrationtests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.anthonycruz.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.anthonycruz.models.Person;
import br.com.anthonycruz.repositories.PersonRepository;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	public PersonRepository repository;

	private static Person person;

	@BeforeAll
	public static void setUp() {
		person = new Person();
	}

	@Test
	@Order(0)
	public void testFindPeopleByName() throws JsonMappingException, JsonProcessingException {
		Pageable pageable = PageRequest.of(1, 2, Sort.by(Direction.DESC, "id"));
		List<Person> people = repository.findPeopleByName("ant", pageable).getContent();

		assertNotNull(people);
		assertEquals(2, people.size());

		person = people.get(0);
		assertEquals(310, person.getId());
		assertEquals("Iolanthe", person.getFirstName());
		assertEquals("Hollyer", person.getLastName());
		assertEquals("5 Rutledge Street", person.getAddress());
		assertEquals("Female", person.getGender());
		assertTrue(person.getEnabled());

		var person1 = people.get(1);
		assertEquals(1, person1.getId());
		assertEquals("Anthony", person1.getFirstName());
		assertEquals("Cruz", person1.getLastName());
		assertEquals("Caçador/SC", person1.getAddress());
		assertEquals("Male", person1.getGender());
		assertTrue(person1.getEnabled());
	}

	@Test
	@Order(0)
	public void testDisableByID() throws JsonMappingException, JsonProcessingException {
		repository.disableById(person.getId());
		person = repository.findById(person.getId()).get();

		assertEquals(310, person.getId());
		assertEquals("Iolanthe", person.getFirstName());
		assertEquals("Hollyer", person.getLastName());
		assertEquals("5 Rutledge Street", person.getAddress());
		assertEquals("Female", person.getGender());
		assertFalse(person.getEnabled());
	}
}
