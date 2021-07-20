package com.example.hackathon_server.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequest {
    private String std_number;
    private String career;
    private String interest;

    public UpdateRequest(String std_number, String career, String interest){
        this.std_number = std_number;
        this.career = career;
        this.interest = interest;
    }
}
