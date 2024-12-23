package com.cf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cf.*")
public class FlashApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashApiApplication.class, args);
        System.out.println("------- Flash Api is running ----------");
    }
}
