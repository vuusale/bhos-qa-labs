package com.example.springproj1;

import org.json.JSONArray;
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

    Set<String> parameters1 = new HashSet<>(Arrays.asList("id", "createdAt", "name"));
    Set<String> parameters2 = new HashSet<>(Arrays.asList("id", "repoId", "createdAt", "name", "updatedAt"));
    Set<String> parameters3 = new HashSet<>(Arrays.asList("id", "branchId", "createdAt", "message", "entry", "sha"));
    Map<String, Set<String>> urls  = new HashMap<String, Set<String>>() {{
        put("/url1", parameters1);
        put("/url2", parameters2);
        put("/url3", parameters3);
    }};

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testUrls() throws Exception {
        for (Map.Entry<String, Set<String>> entry : urls.entrySet()) {
            String url = entry.getKey();
            Set<String> parameters = entry.getValue();

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    createURLWithPort(url), HttpMethod.GET, entity, String.class);

            String actual = response.getBody();

            assertTrue(testValid(parameters, actual));
        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    public boolean testValid(Set<String> parameters, String response) {
        JSONArray objects = new JSONArray(response);
        for (Object object : objects) {
            JSONObject obj = (JSONObject) object;
            Iterator<String> keys = obj.keys();
            while(keys.hasNext()) {
                String property = keys.next();
                if (!parameters.contains(property)) {
                    return false;
                }
            }
        }
        return true;
    }
}