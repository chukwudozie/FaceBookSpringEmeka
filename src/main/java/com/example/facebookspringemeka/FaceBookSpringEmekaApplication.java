package com.example.facebookspringemeka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FaceBookSpringEmekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceBookSpringEmekaApplication.class, args);
    }

}
