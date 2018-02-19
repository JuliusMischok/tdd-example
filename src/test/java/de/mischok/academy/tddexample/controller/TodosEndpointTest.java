package de.mischok.academy.tddexample.controller;

import de.mischok.academy.tddexample.TddExampleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for the todos endpoint.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodosEndpointTest {

	private MockMvc mvc;

	@Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

	/**
	 * Ensures, that reading the complete list of todos works fine.
     *
     * @throws Exception thrown if test fails
	 */
	@Test
	public void readTodos_ok() throws Exception {
	    mvc.perform(get("/todos"))
                .andExpect(status().isOk());
	}

    /**
     * Ensures, that reading, creating, altering and deleting todos works fine.
     * @throws Exception thrown, if test fails
     */
	@Test
    public void workOnTodos_ok() throws Exception {
        // ensure, list is empty
        mvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));

        // add task
        String todo = "{" +
                "\"title\": \"Answer Rob (project timetable)\", " +
                "\"description\": \"Transform information from emails to milestone plan, send it to Rob.\"" +
                "}";
        String location = mvc.perform(post("/todos").content(todo).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

        // check single entry
        mvc.perform(get(location))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Answer Rob (project timetable)")))
                .andExpect(jsonPath("$.description", is("Transform information from emails to milestone plan, send it to Rob.")));

        // update task
        todo = "{" +
                "\"title\": \"Answer Rob (project timetable, milestone plan)\", " +
                "\"description\": \"Transform information from emails to milestone plan, send it to Rob and Jill.\"" +
                "}";
        mvc.perform(MockMvcRequestBuilders.put(location).content(todo).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());

        // check single entry
        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Answer Rob (project timetable, milestone plan)")))
                .andExpect(jsonPath("$.description", is("Transform information from emails to milestone plan, send it to Rob and Jill.")));

        // check whole list
        mvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].title", is("Answer Rob (project timetable, milestone plan)")))
                .andExpect(jsonPath("$[0].description", is("Transform information from emails to milestone plan, send it to Rob and Jill.")));

        // delete task
        mvc.perform(delete(location))
                .andExpect(status().isNoContent());

        // check list is empty
        mvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));

        // check single resource is gone
        mvc.perform(get(location))
                .andExpect(status().isNotFound());
    }
}
