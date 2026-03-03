package com.bsuir_finder;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAdminServer
public class BsuirFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(BsuirFinderApplication.class, args);
	}
}
