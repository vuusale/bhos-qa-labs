package com.example.springproj5;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

public class FirebaseTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    public String authenticate() throws AuthenticationException, NullPointerException {
        JSONObject data = new JSONObject();
        data.put("email", System.getenv("FIREBASE_EMAIL"));
        data.put("password", System.getenv("FIREBASE_PASSWORD"));
        data.put("returnSecureToken", true);
        HttpEntity<String> entity = new HttpEntity<>(data.toString(), headers);

        String url = ApiUrlConstants.FIREBASE_SIGNIN_EMAIL_AND_PASSWORD.concat(System.getenv("FIREBASE_API_KEY"));

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public String getUserDetails(String idToken) {
        headers.put("Authorization", Collections.singletonList("Bearer ".concat(idToken)));
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                ApiUrlConstants.FIRESTORE_USERS.concat(System.getenv("FIREBASE_USER_ID")), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    @Test
    public void endToEndTest() {
        String responseBody = authenticate();
        JSONObject resBody = new JSONObject(responseBody);
        String idToken = (String) resBody.get("idToken");
        String userDetails = new JSONObject(getUserDetails(idToken)).get("fields").toString();
        try {
            User user = new ObjectMapper().readValue(userDetails, User.class);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
}
