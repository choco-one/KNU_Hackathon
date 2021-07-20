package com.example.hackathon_server.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Major {
    ADVANCED("심컴"),GLOBALSW("글솦");

    private String description;
}
