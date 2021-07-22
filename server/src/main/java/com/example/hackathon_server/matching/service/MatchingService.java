package com.example.hackathon_server.matching.service;

import com.example.hackathon_server.matching.domain.Matching;
import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.matching.dto.GAddRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class MatchingService {

    public static final String COLLECTION_NAME = "MATCHING";

    public String add(AddRequest addRequest) throws Exception{

        Matching matching = new Matching(addRequest);

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collection = firestore.collection(COLLECTION_NAME);

        Iterator<DocumentReference> documents = null;
        documents = collection.listDocuments().iterator();

        if(addRequest.getMatchingType().toString().equals("STUDENT")){
            if(addRequest.getUserType().toString().equals("SENIOR")){
                if(addRequest.getMajor().toString().equals("ADVANCED")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("1");
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("2");
                    }
                }
                else if(addRequest.getMajor().toString().equals("GLOBALSW")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("3");
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("4");
                    }
                }
            }
            else if(addRequest.getUserType().toString().equals("FRESHMAN")){
                if(addRequest.getMajor().toString().equals("ADVANCED")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("8");
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("7");
                    }
                }
                else if(addRequest.getMajor().toString().equals("GLOBALSW")){
                    if(addRequest.getGender().toString().equals("MALE")){
                        matching.setMatchingOption("6");
                    }
                    else if(addRequest.getGender().toString().equals("FEMALE")){
                        matching.setMatchingOption("5");
                    }
                }
            }
        }

        while(documents.hasNext()){
            DocumentReference documentReference = documents.next();
            String doc_id = documentReference.getId();

            Matching matching_db = info(doc_id);

            if((Integer.parseInt(matching.getMatchingOption()) + Integer.parseInt(matching_db.getMatchingOption()) == 9)){
                delete(matching_db.getId());
                return matching.getEmail() + " " + matching_db.getEmail();
            }
        }
        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(matching.getId()).set(matching);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String gadd(GAddRequest gaddRequest) throws Exception {
        Matching matching = new Matching(gaddRequest);

        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collection = firestore.collection("GD_MATCHING");

        Iterator<DocumentReference> documents = null;
        documents = collection.listDocuments().iterator();

        if(gaddRequest.getMatchingType().toString().equals("GRADUATE")){
            if(gaddRequest.getUserType().toString().equals("GRADUATE")){
                if(gaddRequest.getCompany().toString().equals("PUBLICCO")){
                    matching.setMatchingOption("1");
                }
                else if(gaddRequest.getMajor().toString().equals("PRIVATECO")){
                    matching.setMatchingOption("2");
                }
            }
            else {
                if(gaddRequest.getMajor().toString().equals("PUBLICCO")){
                    matching.setMatchingOption("4");
                }
                else if(gaddRequest.getMajor().toString().equals("PRIVATECO")){
                    matching.setMatchingOption("3");
                }
            }
        }

        while(documents.hasNext()){
            DocumentReference documentReference = documents.next();
            String doc_id = documentReference.getId();

            Matching matching_db = info(doc_id);

            if((Integer.parseInt(matching.getMatchingOption()) + Integer.parseInt(matching_db.getMatchingOption()) == 5)){
                delete(matching_db.getId());
                return matching.getEmail() + " " + matching_db.getEmail();
            }
        }
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
}
