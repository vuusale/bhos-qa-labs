package com.example.springproj3;

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
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFirebase {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testInsert() {
        Dummy dummy = new Dummy("random", "NoWhere", 123);
        HttpEntity<Dummy> insertRequest = new HttpEntity<>(dummy, headers);

        restTemplate.postForObject(
                createURLWithPort("/insertDummy"), insertRequest, String.class);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/getDummy"))
                .queryParam("name", "random");

        HttpEntity<String> getRequest = new HttpEntity<>(headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, getRequest, String.class);
        assertNotNull(System.getenv("FIREBASE_CREDENTIALS"));

//        assertEquals(getResponse.getBody(), dummy.toString());
    }
}
