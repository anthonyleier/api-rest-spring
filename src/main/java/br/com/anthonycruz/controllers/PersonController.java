package br.com.anthonycruz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.anthonycruz.data.dto.v1.PersonDTO;
import br.com.anthonycruz.data.dto.v2.PersonDTOv2;
import br.com.anthonycruz.services.PersonService;
import br.com.anthonycruz.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/persons")
@Tag(name = "People", description = "Endpoints for managing people")
// @CrossOrigin(origins = {"http://localhost:8080", "https://anthonycruz.com.br"})
public class PersonController {

	@Autowired
	private PersonService service;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Search for all people", description = "Search for all people in the database", tags = {"People"}, responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = { @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public ResponseEntity<Page<PersonDTO>> findAll(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "12") Integer size,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {
		var sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	// @CrossOrigin(origins = {"http://localhost:8080"})
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Search for a person", description = "Searches for a person with a specific ID", tags = {"People"}, responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public PersonDTO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Add a new person", description = "Adds a new person by passing a JSON, XML, or YAML representation", tags = {"People"}, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
		var personDTOResponse = service.create(personDTO);
		return new ResponseEntity<>(personDTOResponse, HttpStatus.CREATED);
	}

	@PostMapping(value = "/v2", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	public PersonDTOv2 createV2(@RequestBody PersonDTOv2 personDTOv2) {
		return service.createV2(personDTOv2);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Update a person", description = "Updates a person by passing a JSON, XML, or YAML representation", tags = {"People"}, responses = {
					@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					})
	public PersonDTO update(@RequestBody PersonDTO personDTO) {
		return service.update(personDTO);
	}
	
	@PatchMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Disable a person by ID", description = "Disable a person with a specific ID", tags = {"People"}, responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public PersonDTO disableById(@PathVariable(value = "id") Long id) {
		return service.disableById(id);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a person", description = "Deletes a person with a specific ID", tags = {"People"}, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
