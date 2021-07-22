package com.example.hackathon_server.matching.domain;

import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.matching.dto.GAddRequest;
import com.example.hackathon_server.user.domain.Company;
import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Access(AccessType.FIELD)
public class Matching {

    String id = UUID.randomUUID().toString();

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private MatchingType matchingType;

    @Enumerated(EnumType.STRING)
    @Column(length=6)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Major major;

    @Enumerated(EnumType.STRING)
    @Column(length = 9)
    private Company company;

    private String matchingOption;

    public Matching(AddRequest addRequest){
        this.email = addRequest.getEmail();
        this.userType = addRequest.getUserType();
        this.matchingType = addRequest.getMatchingType();
        this.gender = addRequest.getGender();
        this.major = addRequest.getMajor();
        this.company = addRequest.getCompany();
    }

    public Matching(GAddRequest gaddRequest){
        this.email = gaddRequest.getEmail();
        this.userType = gaddRequest.getUserType();
        this.matchingType = gaddRequest.getMatchingType();
        this.gender = gaddRequest.getGender();
        this.major = gaddRequest.getMajor();
        this.company = gaddRequest.getCompany();
    }

    public void setMatchingOption(String matchingOption) {
        this.matchingOption = matchingOption;
    }
}
