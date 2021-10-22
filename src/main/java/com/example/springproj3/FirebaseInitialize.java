package com.example.springproj3;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
        try {
//            FileInputStream serviceAccount = new FileInputStream("bhos-qa-labs-firebase-adminsdk-ebidy-71fbaceed6.json");

            JSONObject creds = new JSONObject();
            creds.put("type", System.getenv("FIREBASE_TYPE"));
            creds.put("project_id", System.getenv("FIREBASE_PROJECT_ID"));
            creds.put("private_key_id", System.getenv("FIREBASE_KEY_ID"));
            creds.put("private_key", System.getenv("FIREBASE_PRIVATE_KEY"));
            creds.put("client_email", System.getenv("FIREBASE_CLIENT_EMAIL"));
            creds.put("client_id", System.getenv("FIREBASE_CLIENT_ID"));
            creds.put("auth_uri", System.getenv("FIREBASE_AUTH_URI"));
            creds.put("token_uri", System.getenv("FIREBASE_TOKEN_URI"));
            creds.put("auth_provider_x509_cert_url", System.getenv("FIREBASE_CERT_URL"));
            creds.put("client_x509_cert_url", System.getenv("FIREBASE_CLIENT_CERT_URL"));

            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(creds.toString().getBytes())))
                    .setDatabaseUrl("https://bhos-qa-labs.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}