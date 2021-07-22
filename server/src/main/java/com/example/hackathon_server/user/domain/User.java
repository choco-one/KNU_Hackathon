package com.example.hackathon_server.user.domain;

import com.example.hackathon_server.user.dto.JoinRequest;
import com.example.hackathon_server.user.dto.UpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 9)
    private Company company;

    private String career;

    private String interest;

    private List<String> friend = new ArrayList<>(4);

    private List<String> chatrooms = new ArrayList<>(4);

    public User(JoinRequest joinRequest){
        this.name = joinRequest.getName();
        this.email = joinRequest.getEmail();
        this.password = joinRequest.getPassword();
        this.tel_number = joinRequest.getTel_number();
        this.std_number = joinRequest.getStd_number();
        this.gender = joinRequest.getGender();
        this.userType = joinRequest.getUserType();
        this.major = joinRequest.getMajor();
        this.company = joinRequest.getCompany();
    }

    public void update(UpdateRequest updateRequest){
        this.tel_number = updateRequest.getTel_number();
        this.major = updateRequest.getMajor();
        this.userType = updateRequest.getUserType();
        this.career = updateRequest.getCareer();
        this.interest = updateRequest.getInterest();
    }
}
