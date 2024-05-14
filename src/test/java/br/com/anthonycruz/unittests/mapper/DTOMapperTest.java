package br.com.anthonycruz.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.mapper.DTOMapper;
import br.com.anthonycruz.mapper.mocks.MockPerson;
import br.com.anthonycruz.models.Person;

public class DTOMapperTest {
	MockPerson inputObject;

	@BeforeEach
	public void setUp() {
		inputObject = new MockPerson();
	}

	@Test
	public void parseEntityToDTOTest() {
		PersonDTO output = DTOMapper.parseObject(inputObject.mockEntity(), PersonDTO.class);
		assertEquals(Long.valueOf(0L), output.getKey());
		assertEquals("First Name Test 0", output.getFirstName());
		assertEquals("Last Name Test 0", output.getLastName());
		assertEquals("Address Test 0", output.getAddress());
		assertEquals("Male", output.getGender());
	}

	@Test
	public void parseEntityListToDTOListTest() {
		List<PersonDTO> outputList = DTOMapper.parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
		PersonDTO outputZero = outputList.get(0);

		assertEquals(Long.valueOf(0L), outputZero.getKey());
		assertEquals("First Name Test 0", outputZero.getFirstName());
		assertEquals("Last Name Test 0", outputZero.getLastName());
		assertEquals("Address Test 0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());

		PersonDTO outputSeven = outputList.get(7);

		assertEquals(Long.valueOf(7L), outputSeven.getKey());
		assertEquals("First Name Test 7", outputSeven.getFirstName());
		assertEquals("Last Name Test 7", outputSeven.getLastName());
		assertEquals("Address Test 7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());

		PersonDTO outputTwelve = outputList.get(12);

		assertEquals(Long.valueOf(12L), outputTwelve.getKey());
		assertEquals("First Name Test 12", outputTwelve.getFirstName());
		assertEquals("Last Name Test 12", outputTwelve.getLastName());
		assertEquals("Address Test 12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}

	@Test
	public void parseDTOToEntityTest() {
		Person output = DTOMapper.parseObject(inputObject.mockDTO(), Person.class);
		assertEquals(Long.valueOf(0L), output.getId());
		assertEquals("First Name Test 0", output.getFirstName());
		assertEquals("Last Name Test 0", output.getLastName());
		assertEquals("Address Test 0", output.getAddress());
		assertEquals("Male", output.getGender());
	}

	@Test
	public void parserDTOListToEntityListTest() {
		List<Person> outputList = DTOMapper.parseListObjects(inputObject.mockDTOList(), Person.class);
		Person outputZero = outputList.get(0);

		assertEquals(Long.valueOf(0L), outputZero.getId());
		assertEquals("First Name Test 0", outputZero.getFirstName());
		assertEquals("Last Name Test 0", outputZero.getLastName());
		assertEquals("Address Test 0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());

		Person outputSeven = outputList.get(7);

		assertEquals(Long.valueOf(7L), outputSeven.getId());
		assertEquals("First Name Test 7", outputSeven.getFirstName());
		assertEquals("Last Name Test 7", outputSeven.getLastName());
		assertEquals("Address Test 7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());

		Person outputTwelve = outputList.get(12);

		assertEquals(Long.valueOf(12L), outputTwelve.getId());
		assertEquals("First Name Test 12", outputTwelve.getFirstName());
		assertEquals("Last Name Test 12", outputTwelve.getLastName());
		assertEquals("Address Test 12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}
}
