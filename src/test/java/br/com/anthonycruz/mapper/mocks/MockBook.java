package br.com.anthonycruz.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.models.Book;

public class MockBook {
	public static Book mockEntity() {
		return mockEntity(0L);
	}

	public static List<Book> mockEntityList() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(Long.valueOf(i)));
		}
		return books;
	}

	public static Book mockEntity(Long id) {
		Book book = new Book();
		book.setId(id);
		book.setTitle("Book Title " + id);
		book.setAuthor("Book Author " + id);
		book.setPrice(id.doubleValue());
		book.setLaunchDate(new Date());
		return book;
	}

	public static BookDTO mockDTO() {
		return mockDTO();
	}

	public static List<BookDTO> mockDTOList() {
		List<BookDTO> booksDTO = new ArrayList<BookDTO>();
		for (int i = 0; i < 14; i++) {
			booksDTO.add(mockDTO(Long.valueOf(i)));
		}
		return booksDTO;
	}

	public static BookDTO mockDTO(Long id) {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(id);
		bookDTO.setTitle("Book Title " + id);
		bookDTO.setAuthor("Book Author " + id);
		bookDTO.setPrice(id.doubleValue());
		bookDTO.setLaunchDate(new Date());
		return bookDTO;
	}
}
