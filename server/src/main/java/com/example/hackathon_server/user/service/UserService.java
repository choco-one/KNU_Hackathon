package com.example.hackathon_server.user.service;

import com.example.hackathon_server.user.domain.User;
import com.example.hackathon_server.user.dto.JoinRequest;
import com.example.hackathon_server.user.dto.UpdateRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    public static final String COLLECTION_NAME = "USER";

    public String join(JoinRequest joinRequest) throws Exception{
        User user = new User(joinRequest);

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(user.getEmail()).set(user);

        return apiFuture.get().getUpdateTime().toString();
    }

    public User info(String email) throws Exception{

        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(email);

        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = apiFuture.get();

        if(documentSnapshot.exists()){
            return documentSnapshot.toObject(User.class);
        }
        else {
            return null;
        }
    }

    public String update(String email, UpdateRequest updateRequest) throws Exception {

        User user = info(email);
        user.update(updateRequest);

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(user.getEmail()).set(user);

        return apiFuture.get().getUpdateTime().toString();
    }

    public String addFriend(String email, Map map) throws Exception{

        User user = info(email);

        user.getFriend().add(info(map.get("email").toString()));

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(user.getEmail()).set(user);

        return apiFuture.get().getUpdateTime().toString();
    }

}
