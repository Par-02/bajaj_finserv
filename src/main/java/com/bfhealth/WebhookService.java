package com.bfhealth;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    public void executeProcess() {
        try {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║               BAJAJ FINSERV HEALTH - JAVA QUALIFIER          ║");
            System.out.println("║                   Application Process Started                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();
            
            // Step 1: Generate webhook
            System.out.println("┌──────────────────────────────────────────────────────────────┐");
            System.out.println("│ STEP 1: Generating Webhook                                   │");
            System.out.println("└──────────────────────────────────────────────────────────────┘");
            Map<String, String> webhookResponse = generateWebhook();
            String webhookUrl = webhookResponse.get("webhook");
            String accessToken = webhookResponse.get("accessToken");
            
            System.out.println("✅ Webhook URL: " + webhookUrl);
            System.out.println("✅ Access Token: " + (accessToken != null ? "Received (" + accessToken.length() + " chars)" : "Not received"));
            System.out.println();
            
            // Step 2: Generate SQL query
            System.out.println("┌──────────────────────────────────────────────────────────────┐");
            System.out.println("│ STEP 2: Generating SQL Query for Question 1                  │");
            System.out.println("└──────────────────────────────────────────────────────────────┘");
            String sqlQuery = generateSqlQueryForQuestion1();
            System.out.println("📋 Generated SQL Query:");
            System.out.println("┌──────────────────────────────────────────────────────────────┐");
            System.out.println(formatSqlQuery(sqlQuery));
            System.out.println("└──────────────────────────────────────────────────────────────┘");
            System.out.println();
            
            // Step 3: Submit the solution
            System.out.println("┌──────────────────────────────────────────────────────────────┐");
            System.out.println("│ STEP 3: Submitting Solution to Webhook                       │");
            System.out.println("└──────────────────────────────────────────────────────────────┘");
            submitSolution(webhookUrl, accessToken, sqlQuery);
            
            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    PROCESS COMPLETED SUCCESSFULLY!           ║");
            System.out.println("║                 Solution submitted to Bajaj Finserv Health   ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            
        } catch (Exception e) {
            System.err.println("❌ ❌ ❌ ERROR OCCURRED: " + e.getMessage());
            System.err.println("Stack trace:");
            e.printStackTrace();
        }
    }

    private String formatSqlQuery(String sqlQuery) {
        // Format the SQL query for better readability
        String formatted = sqlQuery
            .replace("SELECT ", "SELECT\n    ")
            .replace("FROM ", "\nFROM\n    ")
            .replace("JOIN ", "\nJOIN\n    ")
            .replace("ON ", "\n    ON ")
            .replace("WHERE ", "\nWHERE\n    ")
            .replace("ORDER BY ", "\nORDER BY\n    ")
            .replace("LIMIT ", "\nLIMIT\n    ");
        
        // Add indentation
        formatted = formatted.replace("\n", "\n│ ");
        return formatted;
    }

    private Map<String, String> generateWebhook() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        
        // Create request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Parth Gupta");
        requestBody.put("regNo", "22BIT0139");
        requestBody.put("email", "parth.gupta2022@vitstudent.ac.in");
        
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        
        System.out.println("🌐 Sending POST request to: " + url);
        System.out.println("📦 Request Body: " + requestBody);
        
        // Make POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        
        System.out.println("📥 Response Status: " + response.getStatusCode());
        
        Map<String, String> responseBody = response.getBody();
        Map<String, String> result = new HashMap<>();
        result.put("webhook", (String) responseBody.get("webhook"));
        result.put("accessToken", (String) responseBody.get("accessToken"));
        
        return result;
    }

    private String generateSqlQueryForQuestion1() {
        // Based on the problem statement from the PDF
        return "SELECT p.AMOUNT AS SALARY, " +
               "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
               "TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
               "d.DEPARTMENT_NAME " +
               "FROM PAYMENTS p " +
               "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
               "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
               "WHERE DAY(p.PAYMENT_TIME) != 1 " +
               "ORDER BY p.AMOUNT DESC " +
               "LIMIT 1";
    }

    private void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        // Create request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("finalQuery", finalQuery);
        
        // Create headers with JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        
        System.out.println("🌐 Submitting to: " + webhookUrl);
        System.out.println("🔐 Using Authorization token: " + (accessToken != null ? "Present" : "Missing!"));
        System.out.println("📤 Sending SQL query...");
        
        // Make POST request using WebClient (reactive)
        WebClient webClient = WebClient.create();
        
        String response = webClient.post()
                .uri(webhookUrl)
                .headers(h -> h.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        System.out.println("📥 Server Response:");
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ " + response);
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }
}