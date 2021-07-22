package com.example.hackathon_server.matching.service;

import com.example.hackathon_server.matching.domain.Matching;
import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.matching.dto.FindRequest;
import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    public static final String COLLECTION_NAME = "MATCHING";

    public String add(AddRequest addRequest) throws Exception{

        Matching matching = new Matching(addRequest);

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getId()).set(matching);

        return apiFuture.get().getUpdateTime().toString();
    }

    public Matching info(String id) throws Exception{
        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);

        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = apiFuture.get();

        if(documentSnapshot.exists()){
            return documentSnapshot.toObject(Matching.class);
        }
        else {
            return null;
        }
    }

    public String delete(String id) throws Exception{

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();

        return "Matching ID: " + id + " deleted";
    }

//    public List<Matching> find(FindRequest findRequest){
//
//        Firestore firestore = FirestoreClient.getFirestore();
//
//        CollectionReference documentSnapshot = firestore.collection();
//
//        if(findRequest.getMatchingType().toString().equals("STUDENT")){
//        }
//
//        else{
//        }
//    }
}
