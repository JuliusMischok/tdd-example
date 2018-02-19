package de.mischok.academy.tddexample.controller;

import de.mischok.academy.tddexample.controller.conversion.TodoDtoToEntityConverter;
import de.mischok.academy.tddexample.controller.conversion.TodoEntityToDtoConverter;
import de.mischok.academy.tddexample.controller.domain.ReadTodoDto;
import de.mischok.academy.tddexample.controller.domain.SaveTodoDto;
import de.mischok.academy.tddexample.domain.Todo;
import de.mischok.academy.tddexample.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller implementation for the todos endpoint.
 */
@RestController
@RequestMapping("todos")
public class TodosController {

    @Autowired
    private TodoRepository repository;

    @Autowired
    private TodoEntityToDtoConverter dtoConverter;

    @Autowired
    private TodoDtoToEntityConverter entityConverter;

    /**
     * GET endpoint for the whole task list.
     * @return HTTP response
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> get() {
        List<ReadTodoDto> todos = repository.findAll()
                .stream()
                .map(dtoConverter::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(todos);
    }

    /**
     * GET endpoint for a single task.
     * @param id the id of the task
     * @return HTTP response
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> get(@PathVariable long id) {
        Todo result = getTodoOrThrow(id);

        return ResponseEntity.ok(dtoConverter.convert(result));
    }

    /**
     * POST endpoint to create a task.
     * @param dto the data transfer representation for the task to be created.
     * @return HTTP response
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> post(@Valid @RequestBody SaveTodoDto dto) {
        Todo todo = entityConverter.convert(dto);

        todo = repository.save(todo);

        URI location = UriComponentsBuilder.fromPath("/todos").pathSegment(String.valueOf(todo.getId())).build().toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * PUT endpoint to update a task.
     * @param id the id of the task
     * @param dto the data transfer representation of the task to be updated.
     * @return HTTP response
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> put(@PathVariable long id, @Valid @RequestBody SaveTodoDto dto) {
        Todo todo = getTodoOrThrow(id);

        todo.setDescription(dto.getDescription());
        todo.setTitle(dto.getTitle());

        todo = repository.save(todo);

        return ResponseEntity.ok(dtoConverter.convert(todo));
    }

    /**
     * DELETE endpoint to remove a task.
     * @param id the id of the task
     * @return HTTP response
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Todo getTodoOrThrow(long id) {
        Todo result = repository.findOne(id);

        return Optional.ofNullable(result)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Exception indicating, that an entity cannot be found by id.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class EntityNotFoundException extends RuntimeException {}
}
