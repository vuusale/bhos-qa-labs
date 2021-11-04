package com.example.springproj5;

import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Iterator;

public class HTTPUtil {

    TestRestTemplate restTemplate = new TestRestTemplate();

    public String get(String url, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String post(String url, JSONObject data, HttpHeaders headers) {
        JSONObject postData = new JSONObject();
        for (Iterator<String> it = data.keys(); it.hasNext(); ) {
            String key = it.next();
            postData.put(key, data.get(key));
        }
        HttpEntity<String> entity = new HttpEntity<>(data.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
