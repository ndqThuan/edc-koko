package com.duro.edc_koko.cloud.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@Service
@RequiredArgsConstructor
public class JedisService {
    private final JedisPooled jedis;

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    public String getCachedToken(String userEmail) {
        return jedis.get("token:" + userEmail);
    }

    public void cacheToken(String userEmail, String token) {
        jedis.setex("token:" + userEmail, 60 * 60, token);
    }
}
