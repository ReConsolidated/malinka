package io.github.reconsolidated.malinka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * main class for application
 */
@SpringBootApplication
@EnableJpaRepositories
public class MalinkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MalinkaApplication.class, args);
	}

}
