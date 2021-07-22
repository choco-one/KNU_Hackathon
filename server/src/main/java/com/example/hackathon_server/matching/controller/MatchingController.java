package com.example.hackathon_server.matching.controller;

import com.example.hackathon_server.matching.domain.Matching;
import com.example.hackathon_server.matching.dto.AddRequest;
import com.example.hackathon_server.matching.dto.FindRequest;
import com.example.hackathon_server.matching.service.MatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/api/matching/add")
    public String join(@RequestParam Map map) throws Exception{
        return matchingService.add(new AddRequest(map));
    }

    @PostMapping("/api/matching/find")
    public List<Matching> find(@RequestParam Map map) throws Exception{
        return matchingService.find(new FindRequest(map));
    }

    @GetMapping("/api/matching/{id}")
    public Matching info(@PathVariable String id) throws Exception{
        return matchingService.info(id);
    }

    @DeleteMapping("/api/matching/{id}")
    public String delete(@PathVariable String id) throws Exception {
        return matchingService.delete(id);
    }
}
