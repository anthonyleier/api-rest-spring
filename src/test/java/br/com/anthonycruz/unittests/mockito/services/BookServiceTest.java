package br.com.anthonycruz.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
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
		Book book = MockBook.mockBook(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		var result = service.findById(testID);

		assertNotNull(result);
		assertNotNull(result.getId());
		// assertNotNull(result.getLinks());
		// assertTrue(result.toString().contains("/books/1"));
		assertEquals("Book Title 1", result.getTitle());
		assertEquals("Book Author 1", result.getAuthor());
		assertEquals(1, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testFindAll() {
		List<Book> bookList = MockBook.mockBookList();

		when(repository.findAll()).thenReturn(bookList);
		var result = service.findAll();

		assertNotNull(result);
		assertEquals(14, result.size());
		
		var bookOne = result.get(1);
		assertNotNull(bookOne.getId());
		//assertNotNull(bookOne.getLinks());
		//assertTrue(bookOne.toString().contains("/books/1"));
		assertEquals("Book Title 1", bookOne.getTitle());
		assertEquals("Book Author 1", bookOne.getAuthor());
		assertEquals(1, bookOne.getPrice());
		assertNotNull(bookOne.getLaunchDate());
		
		var bookFour = result.get(4);
		assertNotNull(bookFour.getId());
		//assertNotNull(bookFour.getLinks());
		//assertTrue(bookFour.toString().contains("/books/4"));
		assertEquals("Book Title 4", bookFour.getTitle());
		assertEquals("Book Author 4", bookFour.getAuthor());
		assertEquals(4, bookFour.getPrice());
		assertNotNull(bookFour.getLaunchDate());
		
		var bookSeven = result.get(7);
		assertNotNull(bookSeven.getId());
		//assertNotNull(bookSeven.getLinks());
		//assertTrue(bookSeven.toString().contains("/books/7"));
		assertEquals("Book Title 7", bookSeven.getTitle());
		assertEquals("Book Author 7", bookSeven.getAuthor());
		assertEquals(7, bookSeven.getPrice());
		assertNotNull(bookSeven.getLaunchDate());
	}

	@Test
	void testCreate() {
		var testID = 2L;

		Book persisted = MockBook.mockBook(testID);
		BookDTO personDTO = MockBook.mockBookDTO(testID);

		when(repository.save(any(Book.class))).thenReturn(persisted);
		var result = service.create(personDTO);

		assertNotNull(result);
		assertNotNull(result.getId());
		// assertNotNull(result.getLinks());
		// assertTrue(result.toString().contains("/books/2"));
		assertEquals("Book Title 2", result.getTitle());
		assertEquals("Book Author 2", result.getAuthor());
		assertEquals(2, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testUpdate() {
		var testID = 3L;

		Book book = MockBook.mockBook(testID);
		Book bookPersisted = book;
		BookDTO bookDTO = MockBook.mockBookDTO(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		when(repository.save(book)).thenReturn(bookPersisted);
		var result = service.update(bookDTO);

		assertNotNull(result);
		assertNotNull(result.getId());
		// assertNotNull(result.getLinks());
		// assertTrue(result.toString().contains("/books/3"));
		assertEquals("Book Title 3", result.getTitle());
		assertEquals("Book Author 3", result.getAuthor());
		assertEquals(3, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testDelete() {
		var testID = 1L;
		Book book = MockBook.mockBook(testID);

		when(repository.findById(testID)).thenReturn(Optional.of(book));
		service.delete(testID);
	}
}
