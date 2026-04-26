package com.ivandev.gestion_alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GestionAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionAlumnosApplication.class, args);
	}

}
