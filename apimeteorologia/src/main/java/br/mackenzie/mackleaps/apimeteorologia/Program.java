package br.mackenzie.mackleaps.apimeteorologia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class Program {

	@GetMapping
	public static void main(String[] args) {
		SpringApplication.run(Program.class, args);
	}

}
