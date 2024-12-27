package com.cromxt.route_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.UUID;

@SpringBootApplication
public class RouteServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RouteServiceApplication.class, args);
	}
}
