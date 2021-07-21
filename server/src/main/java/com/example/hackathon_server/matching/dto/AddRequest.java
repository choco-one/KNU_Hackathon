package com.example.hackathon_server.matching.dto;

import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AddRequest {
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Major major;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public AddRequest(Major major, Gender gender){
        this.major = major;
        this.gender = gender;
    }
}
