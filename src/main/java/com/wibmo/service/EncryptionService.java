package com.wibmo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EncryptionService {
	
	@Value("${api.url}")
	private String apiUrl;
	
	@Value("${TRUE}")
	private String TRUE;
	
	@Value("${FALSE}")
	private String FALSE;
	
	@Value("${TIMEOUT}")
	private int TIMEOUT;
	
	private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
	
    public String compareDatesApiCall(String encryptedData, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access_token", accessToken);

        String jsonBody = "{\"encryptedData\": \"" + encryptedData + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            HttpStatusCode statusCode = responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();
                logger.info("API Response: {}", responseBody);
                return handleResponse(responseBody);
            } else {
                logger.error("Error: {}", statusCode);
                return "{\"error\":\"Error: " + statusCode + "\"}";
            }
        } catch (Exception e) {
            logger.error("Exception occurred while making API call", e);
            return "{\"error\":\"Exception occurred while making API call\"}";
        }
    }

    private String handleResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        
//        int timeout = 3000; 
        
        ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        ((SimpleClientHttpRequestFactory) requestFactory).setConnectTimeout(TIMEOUT);
        ((SimpleClientHttpRequestFactory) requestFactory).setReadTimeout(TIMEOUT);

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            String matchStatus = rootNode.path("match_status").asText();

            if (FALSE.equalsIgnoreCase(matchStatus)) {
            	logger.info("Match status is false. Returning predictions 1.0.");
                return "{\"result\":[{\"predictions\": 1.0,\"Decile_tier\": 0,\"additional_params\":{}}]}";
            } else if (TRUE.equalsIgnoreCase(matchStatus)) {
                return "{\"result\":[{\"predictions\": 2.0,\"Decile_tier\": 2,\"additional_params\":{}}]}";
            }
        } catch (ResourceAccessException e) {
            logger.error("Connection timeout occurred while making API call", e);
            return "{\"result\":[{\"predictions\": 3.0,\"Decile_tier\": 0,\"additional_params\":{}}]}";
        } catch (Exception e) {
            logger.error("Exception occurred while making API call", e);
            return "{\"error\":\"Exception occurred while making API call\"}";
        }
        return "{\"error\":\"Unexpected response\"}";
    }
}
