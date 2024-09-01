package org.example.otp_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OtpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtpServiceApplication.class, args);
    }

}
