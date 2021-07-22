package com.example.hackathon_server.user.dto;

import com.example.hackathon_server.user.domain.Company;
import com.example.hackathon_server.user.domain.Gender;
import com.example.hackathon_server.user.domain.Major;
import com.example.hackathon_server.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Map;

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

    @Column(length = 2)
    private String std_number;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Major major;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Company company;

    public JoinRequest(Map map){
        this.name = map.get("name").toString();
        this.email = map.get("email").toString();
        this.password = map.get("password").toString();
        this.tel_number = map.get("tel_number").toString();
        this.std_number = map.get("std_number").toString();
        this.userType = UserType.valueOf(map.get("userType").toString());
        this.major = Major.valueOf(map.get("major").toString());
        this.gender = Gender.valueOf(map.get("gender").toString());
        this.company = Company.valueOf(map.get("company").toString());
    }
}
