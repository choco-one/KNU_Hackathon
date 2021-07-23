package com.example.hackathon_server.matching.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchingType {
    STUDENT("재학생"), GRADUATE("졸업생");

    private String description;
}
