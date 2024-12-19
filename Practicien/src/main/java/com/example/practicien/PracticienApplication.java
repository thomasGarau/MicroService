package com.example.practicien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PracticienApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticienApplication.class, args);
    }

}
