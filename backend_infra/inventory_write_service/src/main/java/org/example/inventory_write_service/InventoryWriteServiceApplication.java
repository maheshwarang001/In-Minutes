package org.example.inventory_write_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryWriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryWriteServiceApplication.class, args);
    }

}
