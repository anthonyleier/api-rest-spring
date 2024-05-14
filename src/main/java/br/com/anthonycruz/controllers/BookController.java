package br.com.anthonycruz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.anthonycruz.data.dto.v1.BookDTO;
import br.com.anthonycruz.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService service;

	@GetMapping
	public List<BookDTO> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{id}")
	public BookDTO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@PostMapping
	public BookDTO create(@RequestBody BookDTO bookDTO) {
		return service.create(bookDTO);
	}

	@PutMapping
	public BookDTO update(@RequestBody BookDTO bookDTO) {
		return service.update(bookDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
