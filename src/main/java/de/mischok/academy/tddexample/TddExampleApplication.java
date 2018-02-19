package de.mischok.academy.tddexample;

import de.mischok.academy.tddexample.repository.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application class.
 */
@SpringBootApplication
@EnableJpaRepositories
public class TddExampleApplication {

	/**
	 * Main entry point.
	 * @param args the passed command line args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TddExampleApplication.class, args);
	}
}
