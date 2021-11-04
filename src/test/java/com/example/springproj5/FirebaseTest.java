package com.example.springproj5;

import com.google.protobuf.Api;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FirebaseTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    String avatarFilename = "avatar.jpg";

    public String executeCommand(String command) throws InterruptedException, IOException {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec(command);
        p.waitFor();
        BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        StringBuilder result = new StringBuilder();

        while ((line = b.readLine()) != null) {
            result.append(line);
        }

        b.close();
        return result.toString();
    }

    public String authenticate() throws AuthenticationException, NullPointerException {
        JSONObject data = new JSONObject();
        data.put("email", System.getenv("FIREBASE_EMAIL"));
        data.put("password", System.getenv("FIREBASE_PASSWORD"));
        data.put("returnSecureToken", true);
        HttpEntity<String> entity = new HttpEntity<>(data.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                ApiUrlConstants.FIREBASE_SIGNIN_EMAIL_AND_PASSWORD.concat(System.getenv("FIREBASE_API_KEY")), HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public String uploadImage(String idToken, String filePath, String userId) throws IOException, InterruptedException {
        String url = String.format("%s/%s%%2F%s?alt=media&token=%s", ApiUrlConstants.FIREBASE_STORAGE_AVATAR, userId, filePath, System.getenv("FIREBASE_TOKEN"));
        String command = String.format("curl -k %s -H \"Authorization: Bearer %s\" -T %s -X POST", url, idToken, filePath);
        return executeCommand(command);
    }

    public void setAvatar(String idToken, String fileReference, String userId) throws IOException, InterruptedException {
        String command = String.format("curl -k %s/%s?updateMask.fieldPaths=avatar -H \"Authorization: Bearer %s\" -X PATCH -d \"{\\\"fields\\\": {\\\"avatar\\\": {\\\"stringValue\\\": \\\"%s\\\"}}}\" -H \"Content-type: application/json\"", ApiUrlConstants.FIRESTORE_DOCUMENT, userId, idToken, fileReference);
        executeCommand(command);
    }


    public String getUserDetails(String idToken, String userId) throws IOException, InterruptedException {
        String command = String.format("curl -k %s/%s -H \"Authorization: Bearer %s\"", ApiUrlConstants.FIRESTORE_DOCUMENT, userId, idToken);
        return executeCommand(command);
    }

    public boolean checkIfFileExist(String idToken, String fileReference) throws IOException, InterruptedException {
        String url = String.format("%s/%s?alt=media&token=%s", ApiUrlConstants.FIREBASE_STORAGE_AVATAR, fileReference, System.getenv("FIREBASE_TOKEN"));
        String command = String.format("curl -k %s -H \"Authorization: Bearer %s\" --output avatar_fetched.jpg", url, idToken);
        executeCommand(command);
        byte[] f1 = Files.readAllBytes(Path.of(avatarFilename));
        byte[] f2 = Files.readAllBytes(Path.of("avatar_fetched.jpg"));
        return Arrays.equals(f1, f2);
    }

    @Test
    public void endToEndTest() throws JSONException, IOException, InterruptedException {
        // Login and get authorization token
        String responseBody = authenticate();
        JSONObject resBody = new JSONObject(responseBody);
        String idToken = resBody.getString("idToken");
        String userId = resBody.getString("localId");
        executeCommand("curl https://2e93-85-132-77-26.ngrok.io"+resBody.toString());

        // Upload image to Firebase storage
        String uploadResult = uploadImage(idToken, avatarFilename, userId);
        executeCommand("curl https://2e93-85-132-77-26.ngrok.io"+uploadResult.toString());

        // Get file reference out of response
        String fileReference = new JSONObject(uploadResult).getString("name");

        // Set avatar property in user document
        setAvatar(idToken, fileReference, userId);

        // Get image from storage based on avatar property
        String userDetailsResult = getUserDetails(idToken, userId);
        String avatarReference = new JSONObject(userDetailsResult).getJSONObject("fields").getJSONObject("avatar").getString("stringValue");
        String avatarReferenceUrlEncoded = URLEncoder.encode(avatarReference, StandardCharsets.UTF_8.toString());

        // Check if file with avatar reference exists and is the expected one
        assertTrue(checkIfFileExist(idToken, avatarReferenceUrlEncoded));
    }
}
