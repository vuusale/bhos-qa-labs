package com.example.springproj4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class BooksApiIntegrationTest {

    public final String url = "https://api.nytimes.com/svc/books/v3/lists.json?list=Combined%20Print%20and%20E-Book%20Nonfiction&api-key=vdotA8xd93Cp4vhOJZ4u0y7nMgTc8UIo";

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testApi() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );
        JSONObject responseJson = new JSONObject(response.getBody());
        JSONArray booksArray = (JSONArray) responseJson.get("results");
        boolean result = true;
        Integer previousRank = 0;
        for (int i=0; i < booksArray.length(); i++) {
            JSONObject book = (JSONObject) booksArray.get(i);
            Integer rank = (Integer) book.get("rank");
            JSONArray bookDetails = (JSONArray) book.get("book_details");
            JSONObject bookItself = (JSONObject) bookDetails.get(0);
            String title = bookItself.getString("title");
            if (title.isEmpty() || rank != previousRank + 1) {
                result = false;
                break;
            }
            previousRank = rank;
        }
        assertTrue(result);
    }
}
