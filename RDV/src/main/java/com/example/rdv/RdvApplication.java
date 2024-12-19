package com.example.rdv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RdvApplication {

    public static void main(String[] args) {
        SpringApplication.run(RdvApplication.class, args);
    }
}
