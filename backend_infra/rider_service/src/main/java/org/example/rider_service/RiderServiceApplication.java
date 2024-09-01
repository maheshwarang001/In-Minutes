package org.example.rider_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RiderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiderServiceApplication.class, args);
	}

}
