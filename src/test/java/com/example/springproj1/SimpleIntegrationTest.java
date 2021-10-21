package com.example.springproj1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleIntegrationTest {
    
    String[] urls = {
            "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos",
            "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches",
            "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches/1/commits"
    };

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testUrls() {
        for (String url : urls) {
            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        }
    }
}