package br.com.anthonycruz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.mapper.DTOMapper;
import br.com.anthonycruz.models.Book;
import br.com.anthonycruz.repositories.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository repository;

	public BookDTO create(BookDTO bookDTO) {
		Book book = DTOMapper.parseObject(bookDTO, Book.class);
		var savedBook = repository.save(book);
		return DTOMapper.parseObject(savedBook, BookDTO.class);
	}
}
