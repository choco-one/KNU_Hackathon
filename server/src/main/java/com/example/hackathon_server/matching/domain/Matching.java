package com.example.hackathon_server.matching.domain;

import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Access(AccessType.FIELD)
public class Matching {

    String id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    @Column(length=6)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Major major;

    public Matching(AddRequest addRequest){
        this.gender = addRequest.getGender();
        this.major = addRequest.getMajor();
    }
}
