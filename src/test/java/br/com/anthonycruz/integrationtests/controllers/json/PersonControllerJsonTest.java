package br.com.anthonycruz.integrationtests.controllers.json;

import static io.restassured.RestAssured.given;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.anthonycruz.config.TestConfig;
import br.com.anthonycruz.integrationtests.dto.AccountCredentialsDTO;
import br.com.anthonycruz.integrationtests.dto.PersonDTO;
import br.com.anthonycruz.integrationtests.dto.TokenDTO;
import br.com.anthonycruz.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonDTO person;

	private void mockPerson() {
		person.setFirstName("Tony");
		person.setLastName("Stark");
		person.setAddress("10880 Malibu Point, Malibu, CA");
		person.setGender("Male");
		person.setEnabled(true);
	}

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		person = new PersonDTO();
	}
	
	@Test
	@Order(1)
	public void testAuthorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("anthony.cruz", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfig.SERVER_PORT)
					.contentType(TestConfig.CONTENT_TYPE_JSON)
				.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenDTO.class).getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/persons")
				.setPort(TestConfig.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(2)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(person)
				.when()
					.post()
				.then()
					.statusCode(201)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		assertTrue(persistedPerson.getId() > 0);
		assertEquals("Tony", persistedPerson.getFirstName());
		assertEquals("Stark", persistedPerson.getLastName());
		assertEquals("10880 Malibu Point, Malibu, CA", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());
	}
	
	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setFirstName("Anthony");
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(person)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);		
		assertTrue(persistedPerson.getId() > 0);
		assertEquals("Anthony", persistedPerson.getFirstName());
		assertEquals("Stark", persistedPerson.getLastName());
		assertEquals("10880 Malibu Point, Malibu, CA", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());
	}

	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.pathParam("id", person.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		assertEquals(person.getId(), persistedPerson.getId());
		assertEquals("Anthony", persistedPerson.getFirstName());
		assertEquals("Stark", persistedPerson.getLastName());
		assertEquals("10880 Malibu Point, Malibu, CA", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());
	}
	
	@Test
	@Order(5)
	public void testDisable() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.pathParam("id", person.getId())
				.when()
					.patch("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		assertEquals(person.getId(), persistedPerson.getId());
		assertEquals("Anthony", persistedPerson.getFirstName());
		assertEquals("Stark", persistedPerson.getLastName());
		assertEquals("10880 Malibu Point, Malibu, CA", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());
	}	
	
	@Test
	@Order(6)
	public void testDelete() throws JsonMappingException, JsonProcessingException {		
		given()
		.spec(specification)
		.contentType(TestConfig.CONTENT_TYPE_JSON)
		.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
		.pathParam("id", person.getId())
		.when()
			.delete("{id}")
		.then()
			.statusCode(204);
	}
	
	@Test
	@Order(7)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});

		assertNotNull(people);
		assertEquals(20, people.size());

		PersonDTO personTwo = people.get(1);
		assertEquals(2, personTwo.getId());
		assertEquals("Sherlock", personTwo.getFirstName());
		assertEquals("Holmes", personTwo.getLastName());
		assertEquals("221B Baker Street", personTwo.getAddress());
		assertEquals("M", personTwo.getGender());

		PersonDTO personFour = people.get(3);
		assertEquals(4, personFour.getId());
		assertEquals("Harry", personFour.getFirstName());
		assertEquals("Potter", personFour.getLastName());
		assertEquals("4 Privet Drive", personFour.getAddress());
		assertEquals("M", personFour.getGender());

		PersonDTO personSixteen = people.get(15);
		assertEquals(16, personSixteen.getId());
		assertEquals("SpongeBob", personSixteen.getFirstName());
		assertEquals("SquarePants", personSixteen.getLastName());
		assertEquals("Bikini Bottom", personSixteen.getAddress());
		assertEquals("M", personSixteen.getGender());
	}
	
	@Test
	@Order(8)
	public void testFindAllWithoutToken() {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/persons")
				.setPort(TestConfig.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given()
		.spec(specificationWithoutToken)
		.contentType(TestConfig.CONTENT_TYPE_JSON)
		.when()
			.get()
		.then()
			.statusCode(403);
	}
}
