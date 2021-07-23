package com.example.hackathon_server.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Company {
    PUBLICCO("공기업"), PRIVATECO("사기업"), NOJOB("없음");

    private String discription;
}
