package it.objectmethod.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EcommerceDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceDtoApplication.class, args);
	}

}
