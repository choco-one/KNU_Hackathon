package com.example.hackathon_server.user.controller;

import com.example.hackathon_server.user.domain.User;
import com.example.hackathon_server.user.dto.JoinRequest;
import com.example.hackathon_server.user.dto.UpdateRequest;
import com.example.hackathon_server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/join")
    public String join(@RequestBody JoinRequest joinRequest) throws Exception{
        return userService.join(joinRequest);
    }

    @GetMapping("/api/user/{email}")
    public User info(@PathVariable String email) throws Exception {
        return userService.info(email);
    }

    @PutMapping("/api/user/{email}")
    public String update(@PathVariable String email, @RequestBody UpdateRequest updateRequest) throws Exception{
        return userService.update(email, updateRequest);
    }
}
