package br.com.anthonycruz.integrationtests.controllers.json;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.anthonycruz.config.TestConfig;
import br.com.anthonycruz.integrationtests.dto.AccountCredentialsDTO;
import br.com.anthonycruz.integrationtests.dto.TokenDTO;
import br.com.anthonycruz.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {
	private static TokenDTO tokenDTO;

	@Test
	@Order(1)
	public void testSignIn() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("anthony.cruz", "admin123");
		
		tokenDTO = given()
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
						.as(TokenDTO.class);

		assertNotNull(tokenDTO.getAccessToken());
		assertNotNull(tokenDTO.getRefreshToken());
	}

	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		var newTokenDTO = given()
				.basePath("/auth/refresh")
					.port(TestConfig.SERVER_PORT)
					.contentType(TestConfig.CONTENT_TYPE_JSON)
					.pathParam("username", tokenDTO.getUsername())
					.header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
					.extract()
						.body()
							.as(TokenDTO.class);
		
		assertNotNull(newTokenDTO.getAccessToken());;
		assertNotNull(newTokenDTO.getRefreshToken());
	}
}
