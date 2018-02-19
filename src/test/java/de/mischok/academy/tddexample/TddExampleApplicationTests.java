package de.mischok.academy.tddexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Base application test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSpringConfigured
public class TddExampleApplicationTests {

	/**
	 * Ensures, that Spring context is loaded correctly.
	 */
	@Test
	public void contextLoads() {
		assertThat(true, is(true));
	}
}
