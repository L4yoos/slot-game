package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.slot", "com.example.auth"})
public class SlotGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlotGameApplication.class, args);
    }
}