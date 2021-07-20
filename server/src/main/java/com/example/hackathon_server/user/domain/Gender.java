package com.example.hackathon_server.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남자"),FEMALE("여자");

    private String description;
}
