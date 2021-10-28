package com.example.springproj2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.*;
import java.net.URL;
import java.security.cert.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleIntegrationTest {

    public String url = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos";

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testSSLPinning() throws IOException {
        String encodedCert = "";
        String everything;
        try {
            URL https_url = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) https_url.openConnection();
            con.connect();
            Certificate[] certs = con.getServerCertificates();
            Certificate cert = certs[0];
            encodedCert = Base64.getEncoder().encodeToString(cert.getEncoded());
        } catch (SSLPeerUnverifiedException | CertificateEncodingException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("cert.cer"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }
        assertEquals(everything.strip(), encodedCert.strip());
    }

    @Test
    public void testUrl() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);

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