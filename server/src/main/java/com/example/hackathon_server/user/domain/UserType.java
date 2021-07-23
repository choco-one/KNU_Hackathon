package com.example.hackathon_server.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    FRESHMAN("신입생"),SENIOR("재학생"),GRADUATE("졸업생");

    private String description;
}
