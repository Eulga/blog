package com.example.bloghw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogLevel3Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogLevel3Application.class, args);
    }

}
