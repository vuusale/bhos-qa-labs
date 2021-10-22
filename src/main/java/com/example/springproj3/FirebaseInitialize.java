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
import java.io.FileWriter;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
        try {
            FileWriter myWriter = new FileWriter("creds.json");
            myWriter.write(System.getenv("FIREBASE_CREDENTIALS"));
            myWriter.close();
//            FileInputStream serviceAccount = new FileInputStream("bhos-qa-labs-firebase-adminsdk-ebidy-71fbaceed6.json");
            FileInputStream serviceAccount = new FileInputStream("creds.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(System.getenv("FIREBASE_CREDENTIALS").getBytes())))
                    .setDatabaseUrl("https://bhos-qa-labs.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}