package com.example.smaato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.smaato")
public class SmaatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmaatoApplication.class, args);
	}

}
