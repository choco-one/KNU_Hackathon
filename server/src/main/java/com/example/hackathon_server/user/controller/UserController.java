package com.example.hackathon_server.user.controller;

import com.example.hackathon_server.user.domain.User;
import com.example.hackathon_server.user.dto.JoinRequest;
import com.example.hackathon_server.user.service.AuthService;
import com.example.hackathon_server.user.service.InfoService;
import com.example.hackathon_server.user.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private JoinService joinService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private AuthService authService;

    @PostMapping("/api/user/join")
    public String join(@RequestBody JoinRequest joinRequest) throws Exception{
        return joinService.join(joinRequest);
    }

    @GetMapping("/api/user/info/{email}")
    public User info(@PathVariable("email") String email) throws Exception {
        return infoService.info(email);
    }
}
