package br.com.anthonycruz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.anthonycruz.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disableById(@Param("id") Long id);
}
