package com.example.hackathon_server.matching.controller;

import com.example.hackathon_server.matching.domain.Matching;
import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.matching.service.MatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/api/matching/add")
    public String add(@RequestParam Map map) throws Exception{
        return matchingService.add(new AddRequest(map));
    }

    @GetMapping("/api/matching/{email}")
    public Matching info(@PathVariable String email) throws Exception{
        return matchingService.info(email);
    }

    @DeleteMapping("/api/matching/{email}")
    public String delete(@PathVariable String email) throws Exception {
        return matchingService.delete(email);
    }
}
