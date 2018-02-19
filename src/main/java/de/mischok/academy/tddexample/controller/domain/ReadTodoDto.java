package de.mischok.academy.tddexample.controller.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer representation of a task delivered by the service.
 */
@Data
@Builder
public class ReadTodoDto {
    private final long id;
    private final String title;
    private final String description;
}
