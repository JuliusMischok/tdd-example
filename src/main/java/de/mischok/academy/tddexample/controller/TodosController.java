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
