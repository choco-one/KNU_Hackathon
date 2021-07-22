package com.example.hackathon_server.user.controller;

import com.example.hackathon_server.user.domain.User;
import com.example.hackathon_server.user.dto.JoinRequest;
import com.example.hackathon_server.user.dto.UpdateRequest;
import com.example.hackathon_server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/user/join",consumes = "application/x-www-form-urlencoded")
    public String join(@RequestParam Map map) throws Exception{
        return userService.join(new JoinRequest(map));
    }

    @GetMapping("/api/user/{email}")
    public User info(@PathVariable String email) throws Exception {
        return userService.info(email);
    }

    @PutMapping(value = "/api/user/{email}", consumes = "application/x-www-form-urlencoded")
    public String update(@PathVariable String email, @RequestParam Map map) throws Exception{
        return userService.update(email, new UpdateRequest(map));
    }
}
