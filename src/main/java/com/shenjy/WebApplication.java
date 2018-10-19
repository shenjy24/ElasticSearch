package com.shenjy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebApplication.class);
        app.run(args);
    }
}