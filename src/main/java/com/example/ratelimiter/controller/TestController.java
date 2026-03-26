package com.example.ratelimiter.controller;

import com.example.ratelimiter.service.RateLimiterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    private final RateLimiterService rateLimiterService;

    public TestController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/request")
    public String handleRequest(@RequestParam String userId) {
        if (rateLimiterService.allowRequest(userId)) {
            return "Request allowed for user: " + userId;
        } else {
            return "Rate limit exceeded for user: " + userId;
        }
    }
}