package com.example.hanghaeblogprac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HangHaeBlogPracApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangHaeBlogPracApplication.class, args);
    }

}
