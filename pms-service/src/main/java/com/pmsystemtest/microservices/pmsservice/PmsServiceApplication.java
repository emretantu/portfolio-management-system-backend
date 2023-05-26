package com.pmsystemtest.microservices.pmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsServiceApplication.class, args);
	}

}
