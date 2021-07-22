package com.example.hackathon_server.matching.dto;

import com.example.hackathon_server.matching.domain.MatchingType;
import com.example.hackathon_server.user.domain.Company;
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
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddRequest {
    String id = UUID.randomUUID().toString();

    private String email;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 9)
    private Company company;

    public AddRequest(Map map){
        this.email = map.get("email").toString();
        this.userType = UserType.valueOf(map.get("userType").toString());
        this.matchingType = MatchingType.valueOf((map.get("matchingType").toString()));
        this.major = Major.valueOf((map.get("major").toString()));
        this.gender = Gender.valueOf((map.get("gender").toString()));
    }
}
