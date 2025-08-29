package com.bfhealth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println("🚀 Spring Boot Application Starting...");
        System.out.println("⏰ Initializing Bajaj Finserv Health Qualifier Process");
        System.out.println();
        
        try {
            webhookService.executeProcess();
        } catch (Exception e) {
            System.err.println("💥 Critical Error in Application: " + e.getMessage());
            throw e;
        }
    }
}