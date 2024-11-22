package com.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class SpringRestEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestEmployeeApplication.class, args);
	}

}
