package com.example.springproj3;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class DummyService {

    public static final String COL_NAME="dummyCollection";

    public String insertDummyDetails(Dummy dummy) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(dummy.name).set(dummy);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String getDummyDetails(String name) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Dummy dummy = null;

        if(document.exists()) {
//            dummy = document.toObject(Dummy.class);
//            assert dummy != null;
            return Objects.requireNonNull(document.getData()).toString();
        } else {
            return null;
        }
    }
}