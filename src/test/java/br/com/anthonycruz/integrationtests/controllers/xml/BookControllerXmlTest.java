package br.com.anthonycruz.integrationtests.controllers.xml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.anthonycruz.config.TestConfig;
import br.com.anthonycruz.integrationtests.dto.AccountCredentialsDTO;
import br.com.anthonycruz.integrationtests.dto.BookDTO;
import br.com.anthonycruz.integrationtests.dto.TokenDTO;
import br.com.anthonycruz.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerXmlTest extends AbstractIntegrationTest {
	private static RequestSpecification specification;
	private static XmlMapper xmlMapper;
	private static BookDTO book;

	private void mockBook() {
		book.setTitle("Dom Casmurro");
		book.setAuthor("Machado de Assis");
		book.setPrice(50.5);
		book.setLaunchDate(new Date());
	}

	@BeforeAll
	public static void setUp() {
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		book = new BookDTO();
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
				.setBasePath("/books")
				.setPort(TestConfig.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(2)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(book)
				.when()
					.post()
				.then()
					.statusCode(201)
				.extract()
					.body()
						.asString();
		
		BookDTO persistedBook = xmlMapper.readValue(content, BookDTO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
		
		assertTrue(persistedBook.getId() > 0);
		assertEquals("Dom Casmurro", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(50.5, persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
	}
	
	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		book.setTitle("Memórias Póstumas de Brás Cubas");
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.body(book)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookDTO persistedBook = xmlMapper.readValue(content, BookDTO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
		
		assertTrue(persistedBook.getId() > 0);
		assertEquals("Memórias Póstumas de Brás Cubas", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(50.5, persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
	}

	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.pathParam("id", book.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookDTO persistedBook = xmlMapper.readValue(content, BookDTO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
		
		assertTrue(persistedBook.getId() > 0);
		assertEquals("Memórias Póstumas de Brás Cubas", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(50.5, persistedBook.getPrice());
		assertNotNull(persistedBook.getLaunchDate());
	}	
	
	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {		
		given()
		.spec(specification)
		.contentType(TestConfig.CONTENT_TYPE_XML)
		.accept(TestConfig.CONTENT_TYPE_XML)
		.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
		.pathParam("id", book.getId())
		.when()
			.delete("{id}")
		.then()
			.statusCode(204);
	}
	
	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.accept(TestConfig.CONTENT_TYPE_XML)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANTHONYCRUZ)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		List<BookDTO> people = xmlMapper.readValue(content, new TypeReference<List<BookDTO>>() {});

		assertNotNull(people);
		assertEquals(15, people.size());

		BookDTO bookThree = people.get(2);
		assertEquals(3, bookThree.getId());
		assertEquals("Clean Code", bookThree.getTitle());
		assertEquals("Robert C. Martin", bookThree.getAuthor());
		assertEquals(77.0, bookThree.getPrice());
		assertNotNull(bookThree.getLaunchDate());

		BookDTO bookSix = people.get(5);
		assertEquals(6, bookSix.getId());
		assertEquals("Refactoring", bookSix.getTitle());
		assertEquals("Martin Fowler e Kent Beck", bookSix.getAuthor());
		assertEquals(88.0, bookSix.getPrice());
		assertNotNull(bookSix.getLaunchDate());

		BookDTO bookEight = people.get(7);
		assertEquals(8, bookEight.getId());
		assertEquals("Domain Driven Design", bookEight.getTitle());
		assertEquals("Eric Evans", bookEight.getAuthor());
		assertEquals(92.0, bookEight.getPrice());
		assertNotNull(bookEight.getLaunchDate());
	}
	
	@Test
	@Order(7)
	public void testFindAllWithoutToken() {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/books")
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
