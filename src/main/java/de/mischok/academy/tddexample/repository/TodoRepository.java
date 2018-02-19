package de.mischok.academy.tddexample.repository;

import de.mischok.academy.tddexample.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for tasks.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {}
