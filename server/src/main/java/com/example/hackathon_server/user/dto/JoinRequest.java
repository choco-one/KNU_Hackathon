package com.example.hackathon_server.user.dto;

import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank
    @Length(min = 3, max = 10)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    private String password;

    @NotBlank
    @Pattern(regexp = "[0-9]{10,11}")
    private String tel_number;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Major major;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public JoinRequest(String name, String email, String password, String tel_number, UserType userType, Major major, Gender gender){
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel_number = tel_number;
        this.userType = userType;
        this.major = major;
        this.gender = gender;
    }
}
