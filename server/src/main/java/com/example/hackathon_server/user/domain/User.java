package com.example.hackathon_server.user.domain;

import com.example.hackathon_server.user.dto.JoinRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Access(AccessType.FIELD)
public class User {

    private String name;

    private String email;

    private String password;

    @Column(length = 13)
    private String tel_number;

    @Column(length = 2)
    private String std_number;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private Major major;

    private String career;

    private String interest;

    public User(JoinRequest joinRequest){
        this.name = joinRequest.getName();
        this.email = joinRequest.getEmail();
        this.password = joinRequest.getPassword();
        this.tel_number = joinRequest.getTel_number();
        this.gender = joinRequest.getGender();
        this.userType = joinRequest.getUserType();
        this.major = joinRequest.getMajor();
    }

    public void update(String std_number, String career, String interest){
        this.std_number = std_number;
        this.career = career;
        this.interest = interest;
    }
}
