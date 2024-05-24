package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.anthonycruz.integrationtests.dto.BookDTO;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookEmbeddedDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("bookDTOList")
	private List<BookDTO> books;

	public BookEmbeddedDTO() {}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		return Objects.hash(books);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BookEmbeddedDTO other = (BookEmbeddedDTO) obj;
		return Objects.equals(books, other.books);
	}
}
