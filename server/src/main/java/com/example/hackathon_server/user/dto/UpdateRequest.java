package com.example.hackathon_server.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequest {
    private String tel_number;
    private String major;
    private String userType;
    private String career;
    private String interest;

    public UpdateRequest(String tel_number, String major, String userType, String career, String interest){
        this.tel_number = tel_number;
        this.major = major;
        this.userType = userType;
        this.career = career;
        this.interest = interest;
    }
}
