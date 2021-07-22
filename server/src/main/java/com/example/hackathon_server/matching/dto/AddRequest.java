package com.example.hackathon_server.matching.dto;

import com.example.hackathon_server.matching.domain.MatchingType;
import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@NoArgsConstructor
public class AddRequest {
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private MatchingType matchingType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Major major;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public AddRequest(UserType userType, MatchingType matchingType, Major major, Gender gender){
        this.userType = userType;
        this.matchingType = matchingType;
        this.major = major;
        this.gender = gender;
    }

    public AddRequest(Map map){
        this.userType = UserType.valueOf(map.get("userType").toString());
        this.matchingType = MatchingType.valueOf((map.get("matchingType").toString()));
        this.major = Major.valueOf((map.get("major").toString()));
        this.gender = Gender.valueOf((map.get("gender").toString()));
    }
}
