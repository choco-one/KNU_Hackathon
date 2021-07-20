package com.example.hackathon_server.user.service;

import com.example.hackathon_server.user.domain.User;
import com.example.hackathon_server.user.dto.JoinRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    public static final String COLLECTION_NAME = "USER";

    public String join(JoinRequest joinRequest) throws Exception{

        User user = new User(joinRequest);

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(user.getEmail()).set(user);

        return apiFuture.get().getUpdateTime().toString();
    }

}
