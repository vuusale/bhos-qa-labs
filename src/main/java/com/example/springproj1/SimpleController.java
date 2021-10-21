package com.example.springproj1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
public class SimpleController {
    String url1 = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos";
    String url2 = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches";
    String url3 = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches/1/commits";

    Set<String> parameters1 = new HashSet<>(Arrays.asList("id", "createdAt", "name"));
    Set<String> parameters2 = new HashSet<>(Arrays.asList("id", "repoId", "createdAt", "name", "updatedAt"));
    Set<String> parameters3 = new HashSet<>(Arrays.asList("id", "branchId", "createdAt", "message", "entry", "sha"));

    public String get(String url) throws IOException {
        StringBuilder result = new StringBuilder();
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @GetMapping("/url1")
    public String url1Page() throws IOException {
        return get(url1);
    }

    @GetMapping("/url2")
    public String url2Page() throws IOException {
        return get(url2);
    }

    @GetMapping("/url3")
    public String url3Page() throws IOException {
        return get(url3);
    }
}