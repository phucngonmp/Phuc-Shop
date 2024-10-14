package com.phucshop.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@org.springframework.stereotype.Service
public class Service {

    public int decodeBase64Id(String base64Id) {
        // Decode the Base64-encoded string
        byte[] decodedBytes = Base64.getDecoder().decode(base64Id);

        // Parse the decoded bytes as an integer
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return Integer.parseInt(decodedString);
    }
    
    public String checkShow(byte isshow) {
    	return isshow == 1 ? "Showing" : "Hiding";
    }
}

