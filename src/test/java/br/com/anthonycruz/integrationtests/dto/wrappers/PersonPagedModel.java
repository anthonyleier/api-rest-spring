package br.com.anthonycruz.integrationtests.dto.wrappers;

import java.util.List;

import br.com.anthonycruz.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonPagedModel {
	
	@XmlElement(name = "content")
	private List<PersonDTO> content;
	
	public PersonPagedModel() {}

	public List<PersonDTO> getContent() {
		return content;
	}

	public void setContent(List<PersonDTO> content) {
		this.content = content;
	}	
}
