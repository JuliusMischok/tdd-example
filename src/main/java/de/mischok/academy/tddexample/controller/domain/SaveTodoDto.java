package de.mischok.academy.tddexample.controller.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data transfer representation of a task to be saved.
 */
@Data
public class SaveTodoDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;
}
