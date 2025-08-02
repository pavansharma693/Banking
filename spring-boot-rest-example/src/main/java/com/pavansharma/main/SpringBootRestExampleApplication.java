package com.pavansharma.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.pavansharma")
@ComponentScan("com.pavansharma")
@EntityScan("com.pavansharma")
public class SpringBootRestExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestExampleApplication.class, args);
	}
}
