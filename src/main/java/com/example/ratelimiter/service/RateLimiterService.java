package com.example.ratelimiter.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, List<Long>> userRequests = new ConcurrentHashMap<>();

    private static final int LIMIT = 3;
    private static final long WINDOW = 10_000; // 10 seconds

    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();

        userRequests.putIfAbsent(userId, Collections.synchronizedList(new ArrayList<>()));
        List<Long> timestamps = userRequests.get(userId);

        timestamps.removeIf(time -> now - time > WINDOW);

        if (timestamps.size() < LIMIT) {
            timestamps.add(now);
            return true;
        }

        return false;
    }
}