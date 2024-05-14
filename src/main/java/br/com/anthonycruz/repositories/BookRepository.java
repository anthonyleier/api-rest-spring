package br.com.anthonycruz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.anthonycruz.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
