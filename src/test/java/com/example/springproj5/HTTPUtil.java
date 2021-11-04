package com.example.springproj5;

import com.google.api.client.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class HTTPUtil {

    public static String get(String url, HttpHeaders headers) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public static String post(String url, JSONObject data, HttpHeaders headers) {
        TestRestTemplate restTemplate = new TestRestTemplate();
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


    public static String uploadFile(String url, String idToken) throws IOException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(idToken);
        headers.setContentType(MediaType.IMAGE_JPEG);

        Resource res = new InputStreamResource(new FileInputStream("avatar.jpg"));

        HttpEntity<Resource> entity = new HttpEntity<>(res, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
