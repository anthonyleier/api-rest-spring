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

import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.exceptions.RequiredObjectIsNullException;
import br.com.anthonycruz.mapper.mocks.MockBook;
import br.com.anthonycruz.models.Book;
import br.com.anthonycruz.repositories.BookRepository;
import br.com.anthonycruz.services.BookService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	@InjectMocks
	private BookService service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		var testID = 1L;
		Book book = MockBook.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		var result = service.findById(testID);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/books/1"));
		assertEquals("Book Title 1", result.getTitle());
		assertEquals("Book Author 1", result.getAuthor());
		assertEquals(1, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreate() {
		var testID = 2L;

		Book persisted = MockBook.mockEntity(testID);
		BookDTO personDTO = MockBook.mockDTO(testID);

		when(repository.save(any(Book.class))).thenReturn(persisted);
		var result = service.create(personDTO);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/books/2"));
		assertEquals("Book Title 2", result.getTitle());
		assertEquals("Book Author 2", result.getAuthor());
		assertEquals(2, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreateWithNullBook() {
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

		Book book = MockBook.mockEntity(testID);
		Book bookPersisted = book;
		BookDTO bookDTO = MockBook.mockDTO(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		when(repository.save(book)).thenReturn(bookPersisted);
		var result = service.update(bookDTO);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("/books/3"));
		assertEquals("Book Title 3", result.getTitle());
		assertEquals("Book Author 3", result.getAuthor());
		assertEquals(3, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testUpdateWithNullBook() {
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
		Book book = MockBook.mockEntity(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		service.delete(testID);
	}
}
