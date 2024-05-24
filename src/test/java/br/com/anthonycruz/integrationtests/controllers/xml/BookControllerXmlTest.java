package br.com.anthonycruz.integrationtests.controllers.xml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

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
import br.com.anthonycruz.integrationtests.dto.BookDTO;
import br.com.anthonycruz.integrationtests.dto.TokenDTO;
import br.com.anthonycruz.integrationtests.dto.wrappers.BookPagedModel;
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
				.queryParams("page", 1, "size", 10, "direction", "asc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookPagedModel wrapper = xmlMapper.readValue(content, BookPagedModel.class);
		var books = wrapper.getContent();

		assertNotNull(books);
		assertEquals(5, books.size());

		BookDTO book0 = books.get(0);
		assertEquals(11, book0.getId());
		assertEquals("Engenharia de Software: uma abordagem profissional", book0.getTitle());
		assertEquals("Roger S. Pressman", book0.getAuthor());
		assertEquals(56.0, book0.getPrice());
		assertNotNull(book0.getLaunchDate());

		BookDTO book1 = books.get(1);
		assertEquals(12, book1.getId());
		assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", book1.getTitle());
		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", book1.getAuthor());
		assertEquals(54.0, book1.getPrice());
		assertNotNull(book1.getLaunchDate());

		BookDTO book2 = books.get(2);
		assertEquals(13, book2.getId());
		assertEquals("O verdadeiro valor de TI", book2.getTitle());
		assertEquals("Richard Hunter e George Westerman", book2.getAuthor());
		assertEquals(95.0, book2.getPrice());
		assertNotNull(book2.getLaunchDate());
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
