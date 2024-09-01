package org.example.finzo_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FinzoAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinzoAuthServiceApplication.class, args);
	}

}
