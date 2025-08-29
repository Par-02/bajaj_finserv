package com.bfhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BfHealthApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BfHealthApplication.class, args);
        AppRunner appRunner = context.getBean(AppRunner.class);
        try {
            appRunner.run();
        } catch (Exception e) {
            System.err.println("Error running application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}