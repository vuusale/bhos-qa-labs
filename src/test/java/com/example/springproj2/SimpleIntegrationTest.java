package com.example.springproj2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleIntegrationTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testUrl() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos", HttpMethod.GET, entity, String.class);

        String actual = response.getBody();
        assertTrue(testJson(actual));
    }

    public boolean testJson(String response) throws JSONException {
        Set<String> idSet = new HashSet<>();
        Set<String> nameSet = new HashSet<>();
        JSONArray objects = new JSONArray(response);
        int objectsCount =  objects.length();
        System.out.println(objects);
        for (int i=0; i < objects.length(); i++) {
            JSONObject obj = (JSONObject) objects.get(i);
            idSet.add(obj.getString("id"));
            nameSet.add(obj.getString("name"));
        }
        return idSet.size() == objectsCount && nameSet.size() == objectsCount;
    }
}