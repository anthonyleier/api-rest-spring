package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.util.List;

import br.com.anthonycruz.integrationtests.dto.BookDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookPagedModel {
	
	@XmlElement(name = "content")
	private List<BookDTO> content;
	
	public BookPagedModel() {}

	public List<BookDTO> getContent() {
		return content;
	}

	public void setContent(List<BookDTO> content) {
		this.content = content;
	}	
}
