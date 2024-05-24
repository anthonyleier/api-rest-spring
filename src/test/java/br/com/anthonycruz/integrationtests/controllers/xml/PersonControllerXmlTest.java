package br.com.anthonycruz.integrationtests.controllers.xml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.anthonycruz.config.TestConfig;
import br.com.anthonycruz.integrationtests.dto.AccountCredentialsDTO;
import br.com.anthonycruz.integrationtests.dto.PersonDTO;
import br.com.anthonycruz.integrationtests.dto.TokenDTO;
import br.com.anthonycruz.integrationtests.dto.wrappers.PersonPagedModel;
import br.com.anthonycruz.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {
	private static RequestSpecification specification;
	private static XmlMapper xmlMapper;
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
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		person = new PersonDTO();
	}
	
	@Test
	@Order(1)
	public void testAuthorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("anthony.cruz", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfig.SERVER_PORT)
					.contentType(TestConfig.CONTENT_TYPE_XML)
					.accept(TestConfig.CONTENT_TYPE_XML)
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
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(person)
				.when()
					.post()
				.then()
					.statusCode(201)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = xmlMapper.readValue(content, PersonDTO.class);
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
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(person)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = xmlMapper.readValue(content, PersonDTO.class);
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
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.pathParam("id", person.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = xmlMapper.readValue(content, PersonDTO.class);
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
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.pathParam("id", person.getId())
				.when()
					.patch("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonDTO persistedPerson = xmlMapper.readValue(content, PersonDTO.class);
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
		.contentType(TestConfig.CONTENT_TYPE_XML)
		.accept(TestConfig.CONTENT_TYPE_XML)
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
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.queryParams("page", 3, "size", 10, "direction", "asc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonPagedModel wrapper = xmlMapper.readValue(content, PersonPagedModel.class);
		var people = wrapper.getContent();

		assertNotNull(people);
		assertEquals(10, people.size());

		PersonDTO person0 = people.get(0);
		assertEquals(31, person0.getId());
		assertEquals("L;urette", person0.getFirstName());
		assertEquals("Jayne", person0.getLastName());
		assertEquals("8762 Birchwood Point", person0.getAddress());
		assertEquals("Female", person0.getGender());
		assertTrue(person0.getEnabled());

		PersonDTO person1 = people.get(1);
		assertEquals(32, person1.getId());
		assertEquals("Jackie", person1.getFirstName());
		assertEquals("Devanny", person1.getLastName());
		assertEquals("54 Bartelt Crossing", person1.getAddress());
		assertEquals("Male", person1.getGender());
		assertTrue(person1.getEnabled());

		PersonDTO person2 = people.get(2);
		assertEquals(33, person2.getId());
		assertEquals("Bastian", person2.getFirstName());
		assertEquals("Vesty", person2.getLastName());
		assertEquals("2 Bunting Terrace", person2.getAddress());
		assertEquals("Male", person2.getGender());
		assertFalse(person2.getEnabled());
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
		.contentType(TestConfig.CONTENT_TYPE_XML)
		.accept(TestConfig.CONTENT_TYPE_XML)
		.when()
			.get()
		.then()
			.statusCode(403);
	}
}
