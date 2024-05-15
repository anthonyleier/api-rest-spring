package br.com.anthonycruz.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.controllers.BookController;
import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.exceptions.RequiredObjectIsNullException;
import br.com.anthonycruz.exceptions.ResourceNotFoundException;
import br.com.anthonycruz.mapper.DTOMapper;
import br.com.anthonycruz.models.Book;
import br.com.anthonycruz.repositories.BookRepository;

@Service
public class BookService {
	private Logger logger = Logger.getLogger(BookService.class.getName());
	@Autowired
	BookRepository repository;

	public List<BookDTO> findAll() {
		logger.info("Searching for all books");
		var books = repository.findAll();
		var booksDTO = DTOMapper.parseListObjects(books, BookDTO.class);
		booksDTO.stream().forEach(bookDTO -> bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getId())).withSelfRel()));
		return booksDTO;
	}

	public BookDTO findById(Long id) {
		logger.info("Searching for book with ID " + id);
		var book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var bookDTO = DTOMapper.parseObject(book, BookDTO.class);
		bookDTO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookDTO;
	}

	public BookDTO create(BookDTO bookDTO) {
		if (bookDTO == null) throw new RequiredObjectIsNullException();
		logger.info("Creating new book");

		var book = DTOMapper.parseObject(bookDTO, Book.class);
		var savedBook = repository.save(book);
		var savedBookDTO = DTOMapper.parseObject(savedBook, BookDTO.class);

		savedBookDTO.add(linkTo(methodOn(BookController.class).findById(savedBookDTO.getId())).withSelfRel());
		return savedBookDTO;
	}

	public BookDTO update(BookDTO bookDTO) {
		if (bookDTO == null) throw new RequiredObjectIsNullException();
		logger.info("Updating book with ID " + bookDTO.getId());

		var book = repository.findById(bookDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setPrice(bookDTO.getPrice());
		book.setLaunchDate(bookDTO.getLaunchDate());

		var savedBook = repository.save(book);
		var savedBookDTO = DTOMapper.parseObject(savedBook, BookDTO.class);
		savedBookDTO.add(linkTo(methodOn(BookController.class).findById(savedBookDTO.getId())).withSelfRel());
		return savedBookDTO;
	}

	public void delete(Long id) {
		logger.info("Deleting book with ID " + id);
		var book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(book);
	}
}
