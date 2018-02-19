package de.mischok.academy.tddexample.controller.conversion;

import de.mischok.academy.tddexample.controller.domain.ReadTodoDto;
import de.mischok.academy.tddexample.domain.Todo;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter between JPA entity and DTO for tasks.
 */
@Component
public class TodoEntityToDtoConverter implements Converter<Todo, ReadTodoDto> {

    @Override
    public ReadTodoDto convert(@NonNull Todo todo) {
        return ReadTodoDto.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .title(todo.getTitle())
                .build();
    }
}
