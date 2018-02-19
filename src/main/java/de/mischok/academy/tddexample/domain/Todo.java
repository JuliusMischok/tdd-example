package de.mischok.academy.tddexample.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * JPA entity of a task.
 */
@Entity
@Data
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
}
