package com.example.hackathon_server.matching.service;

import com.example.hackathon_server.matching.domain.Matching;
import com.example.hackathon_server.matching.dto.AddRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    public static final String COLLECTION_NAME = "MATCHING";

    public String add(AddRequest addRequest) throws Exception{

        Matching matching = new Matching(addRequest);

        if(addRequest.getMatchingType().toString().equals("STUDENT")){
            if(addRequest.getUserType().toString().equals("SENIOR")){
                if(addRequest.getMajor().toString().equals("ADVANCED")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("1");
                        if(info_option("8") != null){
                            String onDBUser = info_option("8").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("8");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("2");
                        if(info_option("7") != null){
                            String onDBUser = info_option("7").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("7");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                }
                else if(addRequest.getMajor().toString().equals("GLOBALSW")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("3");
                        if(info_option("6") != null){
                            String onDBUser = info_option("6").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("6");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("4");
                        if(info_option("5") != null){
                            String onDBUser = info_option("5").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("5");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                }
            }
            else if(addRequest.getUserType().toString().equals("FRESHMAN")){
                if(addRequest.getMajor().toString().equals("ADVANCED")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("8");
                        if(info_option("1") != null){
                            String onDBUser = info_option("1").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("1");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("7");
                        if(info_option("2") != null){
                            String onDBUser = info_option("2").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("2");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                }
                else if(addRequest.getMajor().toString().equals("GLOBALSW")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("6");
                        if(info_option("3") != null){
                            String onDBUser = info_option("3").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("3");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("5");
                        if(info_option("4") != null){
                            String onDBUser = info_option("4").getEmail();

                            String putDBUser = matching.getEmail();

                            delete("4");

                            return onDBUser + " " + putDBUser;
                        }
                        else{
                            Firestore firestore = FirestoreClient.getFirestore();

                            ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

                            return apiFuture.get().getUpdateTime().toString();
                        }
                    }
                }
            }
        }
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getMatchingOption()).set(matching);

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

    public Matching info_option(String option) throws Exception{
        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(option);

        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = apiFuture.get();

        if(documentSnapshot.exists()){
            return documentSnapshot.toObject(Matching.class);
        }
        else {
            return null;
        }
    }

    public String delete(String option) throws Exception{

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(option).delete();

        return "Matching ID: " + option + " deleted";
    }
}
