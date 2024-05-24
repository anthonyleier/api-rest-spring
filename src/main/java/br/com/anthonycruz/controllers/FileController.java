package br.com.anthonycruz.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.anthonycruz.data.dto.v1.UploadFileResponseDTO;
import br.com.anthonycruz.services.FileStorageService;
import br.com.anthonycruz.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Files", description = "Endpoints for managing files")
@RestController
@RequestMapping("/files")
public class FileController {
	private Logger logger = Logger.getLogger(FileController.class.getName());

	@Autowired
	private FileStorageService service;

	@PostMapping(value = "/upload")
	@Operation(summary = "Store a file", description = "Stores a file in disc", tags = {"Files"}, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	public UploadFileResponseDTO upload(@RequestParam("file") MultipartFile file) {
		logger.info("Storing file to disc");
		var fileName = service.store(file);
		String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/").path(fileName).toUriString();
		return new UploadFileResponseDTO(fileName, fileDownloadURI, file.getContentType(), file.getSize());
	}

}
