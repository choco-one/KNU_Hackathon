package com.example.hackathon_server.user.dto;

import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class UpdateRequest {
    private String tel_number;
    private Major major;
    private UserType userType;
    private String career;
    private String interest;

    public UpdateRequest(Map map){
        this.tel_number = map.get("tel_number").toString();
        this.major = Major.valueOf(map.get("major").toString());
        this.userType = UserType.valueOf(map.get("userType").toString());
        this.career = map.get("career").toString();
        this.interest = map.get("interest").toString();
    }
}
