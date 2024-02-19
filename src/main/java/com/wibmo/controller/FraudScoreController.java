package com.wibmo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.wibmo.bean.FraudScoreRequest;
import com.wibmo.bean.Instances;
import com.wibmo.service.EncryptionService;
import com.wibmo.utility.AESEncryption;


@RestController
@RequestMapping("/api")
public class FraudScoreController {

    @Autowired
    private EncryptionService encryptionService;
    
    @Autowired
    private AESEncryption aesEncryption;
    
    @Value("${aes.key}")
    private String aesKey;
    
    private static final Logger logger = LoggerFactory.getLogger(FraudScoreController.class);

    @PostMapping("/fraudScore")
    public ResponseEntity<String> fraudScore(@RequestHeader String accessToken, @RequestBody FraudScoreRequest request) {
        try {
        	List<Instances> credentialsList = request.getInstances();
        	String apiResponse = null;
        	if(credentialsList!=null && !credentialsList.isEmpty()) {
        		
        		for (Instances cred : credentialsList) {
            		
            		Instances newCred = new Instances();
//                	cred.setIp("49.37.40.104");
//                	cred.setPhone("919874404484");
                	
            		newCred.setIp(cred.getIp());
            		newCred.setMobile_number(cred.getMobile_number());
                	
                	Gson gson = new Gson();
                	String jsonData = gson.toJson(newCred);
                	String encryptedData = aesEncryption.encrypt_gcm(jsonData, aesKey);
                	
                    logger.info("Encrypted Data: {}", encryptedData);
                    
                    String decryptedData = aesEncryption.decrypt_gcm(encryptedData, aesKey);
                    logger.info("Decrypted Data: {}", decryptedData);
                    
                    apiResponse = encryptionService.compareDatesApiCall(encryptedData, accessToken);
                    logger.info("API Response: {}", apiResponse);
                    
        		}                
                return ResponseEntity.ok(apiResponse);
        	}else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
            }
        } catch (Exception e) {
        	logger.error("Error processing the request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request");
        }
    }
}

