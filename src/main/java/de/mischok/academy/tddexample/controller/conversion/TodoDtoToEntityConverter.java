package de.mischok.academy.tddexample.controller.conversion;

import de.mischok.academy.tddexample.controller.domain.SaveTodoDto;
import de.mischok.academy.tddexample.domain.Todo;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter between save task DTO and JPA entity.
 */
@Component
public class TodoDtoToEntityConverter implements Converter<SaveTodoDto, Todo> {
    @Override
    public Todo convert(@NonNull SaveTodoDto saveTodoDto) {
        Todo todo = new Todo();

        todo.setTitle(saveTodoDto.getTitle());
        todo.setDescription(saveTodoDto.getDescription());

        return todo;
    }
}
