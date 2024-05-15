package br.com.anthonycruz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.exceptions.RequiredObjectIsNullException;
import br.com.anthonycruz.exceptions.ResourceNotFoundException;
import br.com.anthonycruz.mapper.DTOMapper;
import br.com.anthonycruz.models.Book;
import br.com.anthonycruz.repositories.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository repository;

	public List<BookDTO> findAll() {
		var books = repository.findAll();
		return DTOMapper.parseListObjects(books, BookDTO.class);
	}

	public BookDTO findById(Long id) {
		Book book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DTOMapper.parseObject(book, BookDTO.class);
	}

	public BookDTO create(BookDTO bookDTO) {
		if (bookDTO == null) throw new RequiredObjectIsNullException();
		Book book = DTOMapper.parseObject(bookDTO, Book.class);
		var savedBook = repository.save(book);
		return DTOMapper.parseObject(savedBook, BookDTO.class);
	}

	public BookDTO update(BookDTO bookDTO) {
		if (bookDTO == null) throw new RequiredObjectIsNullException();
		
		Book book = repository.findById(bookDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setPrice(bookDTO.getPrice());
		book.setLaunchDate(bookDTO.getLaunchDate());

		var savedBook = repository.save(book);
		return DTOMapper.parseObject(savedBook, BookDTO.class);
	}

	public void delete(Long id) {
		var book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(book);
	}
}
